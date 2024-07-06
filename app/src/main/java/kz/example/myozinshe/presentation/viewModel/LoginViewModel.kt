package kz.example.myozinshe.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiIInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.LoginRequest
import kz.example.myozinshe.domain.models.LoginResponse
import java.io.IOException

class LoginViewModel: ViewModel() {
    private val apiService = ServiceBuilder.buildService(ApiIInterface::class.java)

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _emailError = MutableLiveData<Boolean?>()
    val emailError: LiveData<Boolean?> = _emailError

    private val _pawsswordError = MutableLiveData<Boolean?>()
    val passwordError: LiveData<Boolean?> = _pawsswordError



    private val _loginErrorCode = MutableLiveData<Int>()
    val loginErrorCode: LiveData<Int> = _loginErrorCode

    fun isValidInput(email: String, password: String): Boolean {
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password)
        return isEmailValid && isPasswordValid
    }

    private fun isValidEmail(email: String): Boolean {
        val isValid = email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        _emailError.value = !isValid
        return isValid
    }

    private fun isValidPassword(password: String): Boolean {
        val isValid = password.length >= 6
        _pawsswordError.value = !isValid
        return isValid
    }


    fun executeLogin(email: String, password: String) {
        if (!isValidInput(email, password)) {
            return
        }

        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    _loginResponse.value = response.body()
                } else {
                    when (response.code()) {
                        401 -> _loginErrorCode.value = R.string.error_invalid_credentials
                        404 -> _loginErrorCode.value = R.string.error_network
                        502 -> _loginErrorCode.value = R.string.error_network_gateway
                        else -> _loginErrorCode.value = R.string.error_network
                    }
                }
            } catch (e: IOException) {
                _loginErrorCode.value = R.string.error_network
            }  catch (e: Exception) {
                Log.e("LoginError", "Failed to login", e)
                _loginErrorCode.value = R.string.error_network
            }
        }
    }
}