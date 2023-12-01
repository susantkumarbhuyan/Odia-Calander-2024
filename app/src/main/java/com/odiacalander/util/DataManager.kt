package com.odiacalander.util

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.odiacalander.R
import com.odiacalander.dataclasses.MonthEntity
import com.odiacalander.getCurrentYear
import java.nio.charset.Charset

object DataManager {

    var isDataLoaded by mutableStateOf(false)
    var isMonthStartEnd by mutableStateOf(true)

    lateinit var data: MonthEntity
    fun loadAssetsFromJsonFile(context: Context, month: Int) {
        val inputStream = context.resources.openRawResource(
            getMonthJsonFileName(month = month)
        )
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charset.defaultCharset())
        val gson = Gson()
        data = gson.fromJson(json, MonthEntity::class.java)
        isDataLoaded = true
    }

    private fun getMonthJsonFileName(month: Int): Int {
        val currentMont = getMontCalendar()[month]
        Log.d("MONTH ---  ", currentMont.toString())
        if (currentMont < 0) {
            isMonthStartEnd = false
        }
        return currentMont
    }

    val months = listOf(
        "2023_11",
        "2023_12",
        "2024_1",
        "2024_2",
        "2024_3",
        "2024_4",
        "2024_5",
        "2024_6",
        "2024_7",
        "2024_8",
        "2024_9",
        "2024_10",
        "2024_11",
        "2024_12"
    )

    fun getMonthIndex(month: Int, year: Int = getCurrentYear()): Int {

        return months.indexOf("${year}_${month}")
    }

    fun getMontCalendar(): List<Int> {
        return listOf(
            R.raw.november_2023, R.raw.december_2023,
            R.raw.january_2024,
            R.raw.february_2024,
            R.raw.march_2024,
            R.raw.april_2024,
            R.raw.may_2024,
            R.raw.june_2024,
            R.raw.july_2024,
            R.raw.august_2024,
            R.raw.september_2024,
            R.raw.october_2024,
            R.raw.november_2024,
            R.raw.december_2024,
        )
    }
}