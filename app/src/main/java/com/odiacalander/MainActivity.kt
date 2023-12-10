package com.odiacalander

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.odiacalander.core.constants.NOTIFICATION_CHANNEL_ID
import com.odiacalander.core.util.CommonUtils
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.data.CalenderRepository
import com.odiacalander.ui.common.LocalDarkTheme
import com.odiacalander.ui.common.SettingsProvider
import com.odiacalander.ui.screens.HomeEntry
import com.odiacalander.ui.theme.OdiaCalander2024KohinoorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: CalenderRepository
    private var mInterstitialAd: InterstitialAd? = null
    private var adId: String = "ca-app-pub-3940256099942544/1033173712"



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
            }
        reqPermission(requestPermissionLauncher)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val context: Context = this
        createNotificationChannel()
        CoroutineScope(Dispatchers.IO).launch {
            if ((!PreferenceUtil.isDataLoaded() || !repository.isDataAvl()) && CommonUtils.isInternetConnected(
                    context
                )
            ) {
                repository.clearDB()
                repository.storeBlogDataInDB()
            }
        }
        runBlocking {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(PreferenceUtil.getLanguageConfiguration())
            )
        }
        setContent {
            MobileAds.initialize(this) {}
            var adStatus by remember {
                mutableStateOf(false)
            }
            SettingsProvider() {
                OdiaCalander2024KohinoorTheme(darkTheme = LocalDarkTheme.current.isDarkTheme()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        adStatus = showAds(adStatus)
                        HomeEntry {
                            adStatus = it
                        }
                    }
                }
            }

        }
    }

    @Composable
    private fun showAds(adStatus: Boolean): Boolean {
        var adStatus1 = adStatus
        if (adStatus1) {
            loadInterstitialAd {
                adStatus1 = it
            }
            if (adStatus1)
                showInterstitialAd()
            adStatus1 = false
        }
        return adStatus1
    }

    fun loadInterstitialAd(adStatus: (Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
            adId, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d("ADS", adError.toString())
                    mInterstitialAd = null
                    adStatus.invoke(false)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    Log.d("ADS", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    adStatus.invoke(true)
                }
            })
    }

    fun showInterstitialAd() {
        mInterstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    super.onAdDismissedFullScreenContent()
                    Log.d("ADS", "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    // Called when an impression is recorded for an ad.
                    Log.d("ADS", "Ad recorded an impression.")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    // Called when a click is recorded for an ad.
                    Log.d("ADS", "Ad was clicked.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d("ADS", "Ad showed fullscreen content.")
                }
            }
            ad.show(this)
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

    private fun createNotificationChannel() {
        val name = "Festivals"
        val descriptionText = "Daily Festivals Updated"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun reqPermission(requestPermissionLauncher: ActivityResultLauncher<String>){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            else -> {

                requestPermissionLauncher.launch(
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}
