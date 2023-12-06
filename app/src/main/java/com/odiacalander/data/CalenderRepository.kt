package com.odiacalander.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.odiacalander.data.db.CalendarDatabase
import com.odiacalander.data.db.entity.MonthDetailsEntity
import com.odiacalander.data.db.entity.MonthImgsEntity
import com.odiacalander.data.retrofit.retrofit.BloggerApiService
import com.odiacalander.core.util.BLOG_DATA_LOADED
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.WebScarpingManager
import com.odiacalander.models.Horoscope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalenderRepository @Inject constructor(
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

    fun getHoroscope() : Flow<List<Horoscope>> = flow {
        emit(WebScarpingManager.getDailyHoroscopeData())
    }.flowOn(Dispatchers.IO)
}