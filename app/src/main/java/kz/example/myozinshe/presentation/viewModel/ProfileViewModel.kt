package kz.example.myozinshe.presentation.viewModel

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.SelectLanguageModel
import kz.example.myozinshe.domain.models.UserInfo
import java.util.Locale

class ProfileViewModel(application: Application): AndroidViewModel(application ) {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo : LiveData<UserInfo> = _userInfo

    private val _languageSystem = MutableLiveData<SelectLanguageModel>()
    val languageSystem : LiveData<SelectLanguageModel> = _languageSystem

    private val _isDarkModeEnabled = MutableLiveData<Boolean>()
    val isDarkModeEnabled: LiveData<Boolean> = _isDarkModeEnabled

    private val _errorCode = MutableLiveData<Int>()
    val errorCode : LiveData<Int> = _errorCode
    init {
        loadDarkModState()
    }

    fun userInfo(token: String) {
        viewModelScope.launch{
            runCatching {response.getUserInfo("Bearer $token")}
                .onSuccess {
                    _userInfo.value = it
                }.onFailure {
                    _errorCode.value = R.string.error_network
                }
        }
    }

    fun systemLanguage() {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val language = PreferenceProvider(context).getLanguage()
            val locale = when(language) {
                "English" -> Locale("en")
                "Қазақша" -> Locale("kk")
                "Русский" -> Locale("ru")
                else -> Locale("kk")
            }
            updateLocale(locale, context)
            _languageSystem.value = SelectLanguageModel(language ?: "Қазақша")
        }
    }

    private fun updateLocale(locale: Locale, context: Context) {
        Locale.setDefault(locale)
        val config = Configuration().apply {setLocale(locale) }
        context.createConfigurationContext(config)
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            PreferenceProvider(getApplication()).saveDarkModeEnabledState(enabled)
            AppCompatDelegate.setDefaultNightMode(
                if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            _isDarkModeEnabled.value = enabled
        }
    }

    fun loadDarkModState(){
        _isDarkModeEnabled.value = PreferenceProvider(getApplication()).getDarkModeEnabledState()
    }
}