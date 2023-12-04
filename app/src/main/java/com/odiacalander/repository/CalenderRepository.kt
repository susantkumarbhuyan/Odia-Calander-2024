package com.odiacalander.repository

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.odiacalander.db.CalendarDatabase
import com.odiacalander.entity.MonthDetailsEntity
import com.odiacalander.entity.MonthImgsEntity
import com.odiacalander.retrofit.BloggerApiService
import com.odiacalander.util.BLOG_DATA_LOADED
import com.odiacalander.util.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalenderRepository(
    private val apiService: BloggerApiService,
    private val calendarDB: CalendarDatabase
) {
    var isDataLoaded by mutableStateOf(false)
    fun storeBlogDataInDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val monthList = apiService.fetchMonthList()

            if (monthList.body() != null) {
                val content = monthList.body()?.content
                val typeToken = object : TypeToken<List<MonthImgsEntity>>() {}.type
                val data: List<MonthImgsEntity> = Gson().fromJson(content, typeToken)
                calendarDB.calendarDao().storeMonthList(data)
                Log.d("storeBlogDataInDB: ", data.toString())
            }
            isDataLoaded = true
        }
        CoroutineScope(Dispatchers.IO).launch {
            val monthDetails = apiService.fetchMonthDetails()
            if (monthDetails.body() != null) {
                val content = monthDetails.body()?.content
                val typeToken = object : TypeToken<List<MonthDetailsEntity>>() {}.type
                val data: List<MonthDetailsEntity> = Gson().fromJson(content, typeToken)
                calendarDB.calendarDao().storeMonthDetails(data)
                Log.d("storeBlogDataInDB: monthDetails ", data.toString())
            }
            isDataLoaded = true
        }
        if (isDataLoaded)
            PreferenceUtil.updateValue(BLOG_DATA_LOADED, true)
    }

    fun clearDB() {
        CoroutineScope(Dispatchers.IO).launch {
            calendarDB.calendarDao().deleteAllMonthImg()
        }
        CoroutineScope(Dispatchers.IO).launch {
            calendarDB.calendarDao().deleteAllMonthDetails()
        }
    }
}