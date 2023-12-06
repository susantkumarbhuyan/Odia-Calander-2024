package com.odiacalander.core.util

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import com.odiacalander.R
import com.odiacalander.core.util.PreferenceUtil.getInt


private fun getLanguageNumberByCode(languageCode: String): Int =
    languageMap.entries.find { it.value == languageCode }?.key ?: SYSTEM_DEFAULT

fun getLanguageNumber(): Int {
    return if (Build.VERSION.SDK_INT >= 33)
        getLanguageNumberByCode(
            LocaleListCompat.getAdjustedDefault()[0]?.toLanguageTag().toString()
        )
    else LANGUAGE.getInt()
}


@Composable
fun getLanguageDesc(language: Int = getLanguageNumber()): String {
    return stringResource(
        when (language) {
            ODIA -> R.string.la_or
            HINDI -> R.string.la_hi
            ENGLISH -> R.string.la_en_US
            else -> R.string.la_or
        }
    )
}

// Do not modify
private const val ODIA = 1
private const val ENGLISH = 2
private const val HINDI = 3

// Sorted alphabetically
val languageMap: Map<Int, String> = mapOf(
    ODIA to "or",
    HINDI to "hi",
    ENGLISH to "en-US",
)