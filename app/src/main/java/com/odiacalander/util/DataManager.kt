package com.odiacalander.util

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.odiacalander.dataclasses.MonthEntity
import com.odiacalander.getMonths
import java.nio.charset.Charset

object DataManager {

    var isDataLoaded by mutableStateOf(false)

    lateinit var data: MonthEntity
    fun loadAssetsFromJsonFile(context: Context, currentMonth: Int) {
        val month = getMonths()[currentMonth]
        val monthFileName = "${month.month.lowercase()}_${month.year}"
        val inputStream = context.resources.openRawResource(
            context.resources.getIdentifier(
                monthFileName,
                "raw",
                context.packageName
            )
        )
        val size: Int =  inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charset.defaultCharset())
        val gson = Gson()
        data = gson.fromJson(json, MonthEntity::class.java)
        isDataLoaded = true
    }
}