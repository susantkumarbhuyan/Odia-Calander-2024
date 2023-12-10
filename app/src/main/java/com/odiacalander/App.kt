package com.odiacalander

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.odiacalander.core.notification.NotificationWorker
import com.odiacalander.data.db.CalendarDatabase
import com.odiacalander.data.CalenderRepository
import com.odiacalander.data.retrofit.retrofit.BloggerApiService
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var repository: CalenderRepository
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        packageInfo = packageManager.run {
            if (Build.VERSION.SDK_INT >= 33) getPackageInfo(
                packageName, PackageManager.PackageInfoFlags.of(0)
            ) else getPackageInfo(packageName, 0)
        }
        applicationScope = CoroutineScope(SupervisorJob())
        setupWorker()
    }

    private fun setupWorker() {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 2, TimeUnit.HOURS)
                .setConstraints(constraints).build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    companion object {
        lateinit var applicationScope: CoroutineScope
        lateinit var packageInfo: PackageInfo
    }
}