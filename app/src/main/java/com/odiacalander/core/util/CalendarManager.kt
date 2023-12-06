package com.odiacalander.core.util

import android.content.Context
import com.odiacalander.models.Month
import com.odiacalander.data.db.CalendarDatabase
import com.odiacalander.newcalender.core.NewMonth
import kotlinx.coroutines.runBlocking

object CalendarManager {

    fun loadMonthDetailsFromDB(context: Context): NewMonth {
        val database = CalendarDatabase.getDatabase(context)
      return runBlocking {
             database.calendarDao().getMonthData(id = 2122023).toNewMonth()
        }
    }

    suspend fun loadMonthListFromDB(context: Context): List<Month> {
        val database = CalendarDatabase.getDatabase(context)
        return database.calendarDao().getMonthList().map { t -> t.toMonth() }
    }

    suspend fun isDataAvl(context: Context): Boolean {
        val database = CalendarDatabase.getDatabase(context)
        return database.calendarDao().countMonthList() > 0 && database.calendarDao()
            .countMonthData() > 0
    }

}