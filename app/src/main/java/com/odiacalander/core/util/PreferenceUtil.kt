package com.odiacalander.core.util


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import com.google.android.material.color.DynamicColors
import com.odiacalander.App.Companion.applicationScope
import com.odiacalander.R
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val DYNAMIC_COLOR = "dynamic_color"
const val DARK_THEME_VALUE = "dark_theme_value"
const val LANGUAGE = "language"
const val NOTIFICATION = "notification"
const val BLOG_DATA_LOADED = "blog_data_load"
const val DIALY_HOSOSCOPE_ALT_NUMBER = "dailyHoroscopeAltNumber"
const val SYSTEM_DEFAULT = 0
private val BooleanPreferenceDefaults = mapOf(
    NOTIFICATION to true,
    BLOG_DATA_LOADED to false
)

private val IntPreferenceDefaults = mapOf(
    LANGUAGE to SYSTEM_DEFAULT,
    DARK_THEME_VALUE to DarkThemePreference.FOLLOW_SYSTEM,
)


private val kv: MMKV = MMKV.defaultMMKV()

object PreferenceUtil {
    fun String.getInt(default: Int = IntPreferenceDefaults.getOrElse(this) { 0 }): Int =
        kv.decodeInt(this, default)

    fun String.getBoolean(default: Boolean = BooleanPreferenceDefaults.getOrElse(this) { false }): Boolean =
        kv.decodeBool(this, default)

    fun String.updateString(newString: String) = kv.encode(this, newString)

    fun String.updateInt(newInt: Int) = kv.encode(this, newInt)

    fun String.updateBoolean(newValue: Boolean) = kv.encode(this, newValue)
    fun updateValue(key: String, b: Boolean) = key.updateBoolean(b)
    fun encodeInt(key: String, int: Int) = key.updateInt(int)
    fun getValue(key: String): Boolean = key.getBoolean()
    fun encodeString(key: String, string: String) = key.updateString(string)
    fun containsKey(key: String) = kv.containsKey(key)

    fun isDataLoaded() =
        BLOG_DATA_LOADED.getBoolean()

    fun getLanguageConfiguration(languageNumber: Int = kv.decodeInt(LANGUAGE)) =
        languageMap.getOrElse(languageNumber) { "" }


    private fun getLanguageNumberByCode(languageCode: String): Int =
        languageMap.entries.find { it.value == languageCode }?.key ?: SYSTEM_DEFAULT


    fun getLanguageNumber(): Int {
        return if (Build.VERSION.SDK_INT >= 33) getLanguageNumberByCode(
            LocaleListCompat.getAdjustedDefault()[0]?.toLanguageTag().toString()
        )
        else LANGUAGE.getInt()
    }


    fun getDailyHoroscopeAltNumber(): Int {
        var altNum = kv.decodeInt(DIALY_HOSOSCOPE_ALT_NUMBER) + 1
        if(altNum<0)
            altNum= 235
        kv.encode(DIALY_HOSOSCOPE_ALT_NUMBER, altNum)
        return altNum
    }


    private const val TAG = "PreferenceUtil"


    fun modifyDarkThemePreference(
        darkThemeValue: Int = AppSettingsStateFlow.value.darkTheme.darkThemeValue,
    ) {
        applicationScope.launch(Dispatchers.IO) {
            mutableAppSettingsStateFlow.update {
                it.copy(
                    darkTheme = AppSettingsStateFlow.value.darkTheme.copy(
                        darkThemeValue = darkThemeValue,
                    )
                )
            }
            kv.encode(DARK_THEME_VALUE, darkThemeValue)
        }
    }


    data class AppSettings(
        val darkTheme: DarkThemePreference = DarkThemePreference(),
        val isDynamicColorEnabled: Boolean = false,
    )

    private val mutableAppSettingsStateFlow = MutableStateFlow(
        AppSettings(
            DarkThemePreference(
                darkThemeValue = kv.decodeInt(
                    DARK_THEME_VALUE, DarkThemePreference.FOLLOW_SYSTEM
                )
            ),
            isDynamicColorEnabled = kv.decodeBool(
                DYNAMIC_COLOR, DynamicColors.isDynamicColorAvailable()
            )
        )
    )
    val AppSettingsStateFlow = mutableAppSettingsStateFlow.asStateFlow()

}

data class DarkThemePreference(
    val darkThemeValue: Int = FOLLOW_SYSTEM
) {
    companion object {
        const val FOLLOW_SYSTEM = 1
        const val ON = 2
        const val OFF = 3
    }

    @Composable
    fun isDarkTheme(): Boolean {
        return if (darkThemeValue == FOLLOW_SYSTEM) isSystemInDarkTheme()
        else darkThemeValue == ON
    }

    @Composable
    fun getDarkThemeDesc(): String {
        return when (darkThemeValue) {
            FOLLOW_SYSTEM -> stringResource(R.string.follow_system)
            ON -> stringResource(R.string.on)
            else -> stringResource(R.string.off)
        }
    }

}
