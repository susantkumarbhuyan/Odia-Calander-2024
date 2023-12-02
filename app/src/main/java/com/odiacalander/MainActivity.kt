package com.odiacalander

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.odiacalander.ui.common.LocalDarkTheme
import com.odiacalander.ui.common.LocalDynamicColorSwitch
import com.odiacalander.ui.common.SettingsProvider
import com.odiacalander.ui.page.HomeEntry
import com.odiacalander.ui.theme.OdiaCalander2024KohinoorTheme
import com.odiacalander.util.PreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    private var adId: String = "ca-app-pub-3940256099942544/1033173712"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        runBlocking {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(PreferenceUtil.getLanguageConfiguration())
            )
        }
        setContent {
            MobileAds.initialize(this) {}
            SettingsProvider() {
                OdiaCalander2024KohinoorTheme(darkTheme = LocalDarkTheme.current.isDarkTheme()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeEntry()
                    }
                }
            }

        }
    }


    companion object {
        private const val TAG = "MainActivity"
        fun setLanguage(locale: String) {
            Log.d(TAG, "setLanguage: $locale")
            val localeListCompat =
                if (locale.isEmpty()) LocaleListCompat.getEmptyLocaleList()
                else LocaleListCompat.forLanguageTags(locale)
            App.applicationScope.launch(Dispatchers.Main) {
                AppCompatDelegate.setApplicationLocales(localeListCompat)
            }
        }

    }
}

