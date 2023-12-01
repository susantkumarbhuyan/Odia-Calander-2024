package com.odiacalander.screens

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.odiacalander.App
import com.odiacalander.MainActivity
import com.odiacalander.R
import com.odiacalander.util.LANGUAGE
import com.odiacalander.util.PreferenceUtil
import com.odiacalander.util.PreferenceUtil.getLanguageConfiguration
import com.odiacalander.util.getLanguageDesc
import com.odiacalander.util.getLanguageNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HoroscopScreen() {
    TestLanguage()
}

@Preview(showBackground = true)
@Composable
fun TestLanguage() {
    val context = LocalContext.current
    var lag by remember {
        mutableStateOf("")
    }
    var language by remember { mutableIntStateOf(PreferenceUtil.getLanguageNumber()) }
    fun setLanguage(selectedLanguage: Int) {
        language = selectedLanguage
        PreferenceUtil.encodeInt(LANGUAGE, language)
      //  MainActivity.setLanguage(getLanguageConfiguration())
      changeLang(getLanguageConfiguration())
    }
    val name :String = ""
    val id  =LocalContext.current.resources.getIdentifier(name, "string", context.packageName)
    Column {
        Text(text = stringResource(id))
        Button(onClick = {
            setLanguage(1)
        }) {
            Text(text = "Odia")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            setLanguage(2)
        }) {
            Text(text = "English")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            setLanguage(3)
        }) {
            Text(text = "Hindi")
        }
        Text(text = lag.toString())
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            lag = LocaleListCompat.getAdjustedDefault()[0]?.toLanguageTag().toString()
            Log.d("LOC_", lag)

        }) {
            Text(text = "Check LAng")
        }
    }

}

fun changeLang(locale: String) {
    Log.d("LOCALLL", "setLanguage: $locale")
    val localeListCompat =
        LocaleListCompat.forLanguageTags(locale)

    AppCompatDelegate.setApplicationLocales(localeListCompat)

}