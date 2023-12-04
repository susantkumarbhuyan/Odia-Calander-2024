package com.odiacalander.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.odiacalander.dataclasses.Horoscope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


object WebScarpingManager {
    var DAILY_HOROSCOPE_URL =
        "https://www.dharitri.com/horoscope-${getDateOrdinalSuffix(getCurrentDate().toInt())}-${getCurrentMonthName().lowercase()}-${getCurrentYear()}/"
    var isDataLoaded by mutableStateOf(false)
    lateinit var data: List<Horoscope>
    suspend fun getDailyHoroscope() {
        val doc: Document =
            Jsoup.connect("https://www.dharitri.com/horoscope-3rd-december-2023/").get()
        val doc2 = doc.select("div.p-t-20 div.row")
        data = doc2.map { e ->
            Horoscope(
                rashiId = 1,
                rashi = e.select("div.col-md-2 strong").text(),
                details = e.select("div.col-md-10").text()
            )
        }
        isDataLoaded = true
    }

    // https://www.dharitri.com/daily-horoscope-234/
    //https://jantrajyotisha.com/yearly-mithuna-rashiphala/
    //https://jantrajyotisha.com/monthly-mithuna/
    //https://jantrajyotisha.com/weekly-horoscope/weekly-mithuna/
    //https://jantrajyotisha.com/daily-mithuna/
    fun getDailyHoroscopeData(): List<Horoscope> {
        val doc: Document =
            Jsoup.connect(DAILY_HOROSCOPE_URL).get()
        val doc2 = doc.select("div.p-t-20 div.row")
        var list: List<Horoscope> = emptyList()
        if (doc2.isEmpty()) {
            DAILY_HOROSCOPE_URL =
                "https://www.dharitri.com/daily-horoscope-${PreferenceUtil.getDailyHoroscopeAltNumber()}"
            getDailyHoroscopeData()
        } else {
            list = doc2.map { e ->
                Horoscope(
                    rashiId = 1,
                    rashi = e.select("div.col-md-2 strong").text(),
                    details = e.select("div.col-md-10").text()
                )
            }
        }
        return list
    }

}