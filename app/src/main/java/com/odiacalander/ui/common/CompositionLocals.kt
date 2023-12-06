package com.odiacalander.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import com.odiacalander.core.util.DarkThemePreference
import com.odiacalander.core.util.PreferenceUtil

val LocalDarkTheme = compositionLocalOf { DarkThemePreference() }
val LocalDynamicColorSwitch = compositionLocalOf { false }

@Composable
fun SettingsProvider( content: @Composable () -> Unit) {
    PreferenceUtil.AppSettingsStateFlow.collectAsState().value.run {
        CompositionLocalProvider(
            LocalDarkTheme provides darkTheme,
            LocalDynamicColorSwitch provides isDynamicColorEnabled,
            content = content
        )
    }
}