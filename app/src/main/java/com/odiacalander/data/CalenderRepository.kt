package com.odiacalander.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.odiacalander.core.util.BLOG_DATA_LOADED
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.WebScarpingManager
import com.odiacalander.data.db.CalendarDatabase
import com.odiacalander.models.entity.MonthDetailsEntity
import com.odiacalander.models.entity.MonthImgsEntity
import com.odiacalander.data.retrofit.retrofit.BloggerApiService
import com.odiacalander.models.Horoscope
import com.odiacalander.models.Month
import com.odiacalander.newcalender.core.NewMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CalenderRepository @Inject constructor(
    private val apiService: BloggerApiService,
    private val calendarDB: CalendarDatabase
) {
    var isDataLoaded by mutableStateOf(false)

    private val _monthImg = MutableStateFlow<Month>(Month())
    val monthImg: StateFlow<Month>
        get() = _monthImg

    suspend fun storeBlogDataInDB() {
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

    suspend fun clearDB() {
        CoroutineScope(Dispatchers.IO).launch {
            calendarDB.calendarDao().deleteAllMonthImg()
        }
        CoroutineScope(Dispatchers.IO).launch {
            calendarDB.calendarDao().deleteAllMonthDetails()
        }
    }

    suspend fun isDataAvl(): Boolean {
        return calendarDB.calendarDao().countMonthList() > 0 && calendarDB.calendarDao()
            .countMonthData() > 0
    }


    suspend fun loadMonthDetailsFromDB(monthId: Int): NewMonth {
        return runBlocking {
            val result = calendarDB.calendarDao().getMonthData(id = monthId)
            if (result != null) result.toNewMonth() else NewMonth()
        }
    }

    suspend fun getMonthDetails(monthId: Int): Flow<NewMonth> = flow {
        val result = calendarDB.calendarDao().getMonthData(id = monthId)
        emit(if (result != null) result.toNewMonth() else NewMonth())
    }.flowOn(Dispatchers.IO)

    suspend fun getYearlyFestival(year: Int, langId: Int): Flow<List<MonthDetailsEntity>> = flow {
        val result = calendarDB.calendarDao().getYearlyFestival(year = year,langId=langId)
        emit(result)
    }.flowOn(Dispatchers.IO)

    suspend fun getHoroscope(): Flow<List<Horoscope>> = flow {
        emit(WebScarpingManager.getDailyHoroscopeData())
    }.flowOn(Dispatchers.IO)

    suspend fun getMonthImgs(): Flow<List<Month>> = flow {
        emit(calendarDB.calendarDao().getMonthList().map { t -> t.toMonth() })
    }.flowOn(Dispatchers.IO)

    suspend fun getCurrentMonthImg(monthId: Int) {
        val result = calendarDB.calendarDao().getMonthImgs(monthId)
        if (result != null) {
            _monthImg.emit(result.toMonth())
        }

    }

}