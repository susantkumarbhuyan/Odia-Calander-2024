package com.odiacalander.core.util

import com.odiacalander.models.Horoscope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


object WebScarpingManager {
    val months = arrayOf(
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december"
    )
    var DAILY_HOROSCOPE_URL =
        "https://www.dharitri.com/horoscope-${getDateOrdinalSuffix(getCurrentDate().toInt())}-${months[getCurrentMonth() - 1]}-${getCurrentYear()}/"

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