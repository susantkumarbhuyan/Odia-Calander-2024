package com.odiacalander.util

import android.content.Context
import com.odiacalander.dataclasses.Month
import com.odiacalander.db.CalendarDatabase
import com.odiacalander.newdataclasses.NewMonth
import kotlinx.coroutines.runBlocking

object CalendarManager {

    fun loadMonthDetailsFromDB(context: Context): NewMonth {
        val database = CalendarDatabase.getDatabase(context)
      return runBlocking {
            val t = database.calendarDao().getMonthData(id = 2122023)
           NewMonth(t)
        }
    }

    suspend fun loadMonthListFromDB(context: Context): List<Month> {
        val database = CalendarDatabase.getDatabase(context)
        return database.calendarDao().getMonthList().map { t -> Month(t) }
    }

    suspend fun isDataAvl(context: Context): Boolean {
        val database = CalendarDatabase.getDatabase(context)
        return database.calendarDao().countMonthList() > 0 && database.calendarDao()
            .countMonthData() > 0
    }

}