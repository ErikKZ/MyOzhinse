package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.UserInfo
import kz.example.myozinshe.domain.models.UserInfoRequest

class InfoViewModel : ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    private val _isUserUdpate = MutableLiveData<Boolean>()
    val isUserUpdate: LiveData<Boolean> = _isUserUdpate

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun getInfo(token: String) {
        viewModelScope.launch {
            runCatching { response.getUserInfo("Bearer $token") }
                .onSuccess {
                    _userInfo.value = it
                }.onFailure {
                    _errorCode.value = R.string.error_info_response
                }
        }
    }

    fun updateInfo(token: String, userUpdateInfo: UserInfoRequest) {
        viewModelScope.launch {
            runCatching { response.updateUserInfo("Bearer $token", userUpdateInfo) }
                .onSuccess {
                    _isUserUdpate.value = true
                }.onFailure {
                    _errorCode.value = R.string.error_info_update
                }
        }
    }
}