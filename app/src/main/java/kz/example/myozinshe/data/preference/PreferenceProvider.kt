package kz.example.myozinshe.data.preference

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_TOKEN = "SAVE_TOKEN"
private const val SHARED_LANGUAGE = "SAVE_LANGUAGE"
private const val SHARED_KEY = "MYOZINSHEAPP"
class PreferenceProvider(context: Context) {
    private val preference: SharedPreferences = context.applicationContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    fun clearShared() = preference.edit().clear().apply()

    fun saveLanguage(language: String) = preference.edit().putString(SHARED_LANGUAGE, language).apply()

    fun getLanguage(): String? = preference.getString(SHARED_LANGUAGE, null)

    fun saveToken(savedAt: String) = preference.edit().putString(SHARED_TOKEN, savedAt).apply()

    fun getToken(): String? = preference.getString(SHARED_TOKEN, null)

    fun saveDarkModeEnabledState(enabled: Boolean) = preference.edit().putBoolean("DarkMode", enabled).apply()

    fun getDarkModeEnabledState(): Boolean = preference.getBoolean("DarkMode", false)

}