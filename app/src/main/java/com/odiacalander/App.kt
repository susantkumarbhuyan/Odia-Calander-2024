package com.odiacalander

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.odiacalander.db.CalendarDatabase
import com.odiacalander.repository.CalenderRepository
import com.odiacalander.retrofit.BloggerApiService
import com.odiacalander.retrofit.RetrofitHelper
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {
    lateinit var calenderRepository: CalenderRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
        MMKV.initialize(this)
        packageInfo = packageManager.run {
            if (Build.VERSION.SDK_INT >= 33) getPackageInfo(
                packageName, PackageManager.PackageInfoFlags.of(0)
            ) else getPackageInfo(packageName, 0)
        }
        applicationScope = CoroutineScope(SupervisorJob())
    }

    private fun initialize() {
        val apiService = RetrofitHelper.getInstance().create(BloggerApiService::class.java)
        val database = CalendarDatabase.getDatabase(applicationContext)
        calenderRepository = CalenderRepository(apiService = apiService, calendarDB = database)
    }

    companion object {
        lateinit var applicationScope: CoroutineScope
        lateinit var packageInfo: PackageInfo
    }
}