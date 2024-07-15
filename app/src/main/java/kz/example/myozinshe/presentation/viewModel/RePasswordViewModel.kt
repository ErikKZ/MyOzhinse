package kz.example.myozinshe.presentation.viewModel

import android.media.session.MediaSession.Token
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.ChangePasswordRequest
import kz.example.myozinshe.domain.models.ChangePasswordResponse
import kz.example.myozinshe.domain.models.UserInfo

class RePasswordViewModel: ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _changePasswordResponse = MutableLiveData<UserInfo>()
    val changePasswordResponse: LiveData<UserInfo> = _changePasswordResponse

    private val _passwordValidationState = MutableLiveData<Boolean>()
    val passwordValidationState: LiveData<Boolean> = _passwordValidationState

    private val _passwordLengthState = MutableLiveData<Boolean>()
    val passwordLengthState: LiveData<Boolean> = _passwordLengthState

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun updatePassword(token: String, password: String,password2: String ) {
        if (!(isValidPassword(password, password2) && isValidPasswordLenght(password))) {
            return
        }

        viewModelScope.launch {
            runCatching { response.updateUserPassword("Bearer $token", ChangePasswordRequest(password)) }
                .onSuccess {
                    _changePasswordResponse.value = it
                }.onFailure {
                    _errorCode.value =  R.string.error_info_update
                }
        }
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