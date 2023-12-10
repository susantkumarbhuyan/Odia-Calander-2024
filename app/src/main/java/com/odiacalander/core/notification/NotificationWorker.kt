package com.odiacalander.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.odiacalander.App
import com.odiacalander.MainActivity
import com.odiacalander.R
import com.odiacalander.core.constants.NOTIFICATION_CHANNEL_ID
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.fullWeeksNameOdia
import com.odiacalander.core.util.getCurrentDate
import com.odiacalander.core.util.getCurrentMonth
import com.odiacalander.core.util.getCurrentMonthName
import com.odiacalander.core.util.getCurrentWeek
import com.odiacalander.core.util.getCurrentYear
import com.odiacalander.core.util.localeDates
import com.odiacalander.core.util.localeMonths
import com.odiacalander.core.util.localeYears
import com.odiacalander.core.util.lunarDays
import com.odiacalander.data.CalenderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationWorker @Inject constructor(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.d("WORKER", "WORKER CALLED")
        val repository = (context as App).repository
        CoroutineScope(Dispatchers.IO).launch {
            val festList = mutableListOf<String>()
            val monthId =
                "${PreferenceUtil.getLanguageNumber()}${getCurrentMonth()}${getCurrentYear()}".toInt()
            val month = repository.loadMonthDetailsFromDB(monthId = monthId)
            val todayFestival = month.festivals[getCurrentDate().toString()]
            val todayLunarDay = month.lunarDays[getCurrentDate().toString()]
            todayFestival?.joinToString(", ")?.let { festList.add(it) }
            if (todayLunarDay != null) {
                festList.add(lunarDays[todayLunarDay.toInt()])
            }

            if (festList.isNotEmpty()) {

                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent: PendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                val st = context.getResources()
                val title = "${st.getString(R.string.datePrefix)}: ${st.getString(localeDates[getCurrentDate()]!!)} ${
                    st.getString(
                        localeMonths[getCurrentMonth()]!!
                    )
                } ${
                    st.getString(
                        localeYears[getCurrentYear()]!!
                    )
                }"
                val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(Icon.createWithResource(context,R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setContentText("ଆଜିର ଦିନ : ${fullWeeksNameOdia[getCurrentWeek()]} " + festList.joinToString(", "))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                with(NotificationManagerCompat.from(context)) {
                    val notificationId: Int =
                        "${getCurrentHourUsingTimeUnit()}${getCurrentDate()}".toInt()
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    }
                    notify(notificationId, builder.build())
                }
            }

        }

        return Result.success()
    }

    fun getCurrentHourUsingTimeUnit(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val hours = TimeUnit.MILLISECONDS.toHours(currentTimeMillis)
        return (hours % 24).toInt()
    }
}