package kz.example.myozinshe.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.LoginRequest
import kz.example.myozinshe.domain.models.LoginResponse
import java.io.IOException

class LoginViewModel: ViewModel() {
    private val apiService = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _loginBody = MutableLiveData<LoginResponse>()
    val loginBody: LiveData<LoginResponse> = _loginBody

    private val _emailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean> = _emailError

    private val _pawsswordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> = _pawsswordError



    private val _loginErrorCode = MutableLiveData<Int>()
    val loginErrorCode: LiveData<Int> = _loginErrorCode

    fun isValidInput(email: String, password: String): Boolean {
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password)
        return isEmailValid && isPasswordValid
    }

    private fun isValidEmail(email: String): Boolean {
        val isValid = email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
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
            runCatching {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    _loginBody.value = response.body()
                    Log.d("AAA", "executeLogin: ${response.body()}")
                } else {
                    Log.d("AAA", "error: ${response.code()}")
                    when (response.code()) {
                        401 -> _loginErrorCode.value = R.string.error_invalid_credentials
                        404 -> _loginErrorCode.value = R.string.error_network
                        502 -> _loginErrorCode.value = R.string.error_network_gateway
                        else -> _loginErrorCode.value = R.string.error_network
                    }
                }
            }.onFailure { e ->
                Log.e("LoginError", "Failed to login", e)
                _loginErrorCode.value = R.string.error_network
            }
        }
    }
}