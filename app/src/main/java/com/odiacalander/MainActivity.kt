package com.odiacalander

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.google.android.gms.ads.MobileAds
import com.odiacalander.core.notification.RemindersManager
import com.odiacalander.core.notification.reminders_notification_channel_id
import com.odiacalander.core.notification.reminders_notification_channel_name
import com.odiacalander.ui.common.LocalDarkTheme
import com.odiacalander.ui.common.SettingsProvider
import com.odiacalander.ui.screens.HomeEntry
import com.odiacalander.ui.theme.OdiaCalander2024KohinoorTheme
import com.odiacalander.core.util.CalendarManager
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.data.CalenderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: CalenderRepository

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cont = application

        runBlocking {
            if (!PreferenceUtil.isDataLoaded() || !CalendarManager.isDataAvl(cont)) {
                repository.clearDB()
                repository.storeBlogDataInDB()
            }

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

    private fun createNotificationsChannels() {
        val channel = NotificationChannel(
            reminders_notification_channel_id, reminders_notification_channel_name,
            NotificationManager.IMPORTANCE_HIGH
        )
        ContextCompat.getSystemService(this, NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }
}
