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

class RegistrationViewModel: ViewModel() {
    private val apiService = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _registerBody = MutableLiveData<LoginResponse>()
    val registerBody: LiveData<LoginResponse> = _registerBody

    private val _passwordValidationState = MutableLiveData<Boolean>()
    val passwordValidationState: LiveData<Boolean> = _passwordValidationState

    private val _passwordLengthState = MutableLiveData<Boolean>()
    val passwordLengthState: LiveData<Boolean> = _passwordLengthState


    private val _emailValidationState = MutableLiveData<Boolean>()
    val emailValidationState: LiveData<Boolean> = _emailValidationState

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun register(email: String, password: String, password2: String) {
        if (!isValidInput(email, password, password2)) {
            return
        }

        viewModelScope.launch {
            runCatching {
                val response = apiService.regIn(LoginRequest(email, password))
                if (response.isSuccessful) {
                    _registerBody.value = response.body()
                } else {
                    _errorCode.value = R.string.error_invalid_credentials
                }
            }.onFailure {
                Log.e("RegistrationError", "Failed to register", it)
                _errorCode.value = R.string.error_network
            }
        }
    }

    fun isValidInput(email: String, password: String, password2: String): Boolean {
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password,password2)
        val isPasswordLenthgValid = isValidPasswordLenght(password2)

        return isEmailValid && isPasswordValid &&  isPasswordLenthgValid
    }

    private fun isValidEmail(email: String): Boolean {
        val isValid = email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        _emailValidationState.value = isValid
        return isValid
    }

    private fun isValidPassword(password: String, password2: String): Boolean {
        val isValid = password == password2
        _passwordValidationState.value = isValid
        return isValid
    }
    private fun isValidPasswordLenght(password: String): Boolean {
        val isValid = password.length >= 6
        _passwordLengthState.value = isValid
        return isValid
    }


}