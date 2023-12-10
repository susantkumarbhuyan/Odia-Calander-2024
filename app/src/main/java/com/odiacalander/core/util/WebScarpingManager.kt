package com.odiacalander.core.util

import com.odiacalander.models.Horoscope
import org.jsoup.HttpStatusException
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

  //https://www.astrosage.com/odia/rasifala/monthly/brusha-rasifala.asp
    //https://www.astrosage.com/2024/rashiphala-2024.asp
    fun getDailyHoroscopeData(): List<Horoscope> {
        return try {
            getDailyData(DAILY_HOROSCOPE_URL)
        } catch (_: HttpStatusException) {
            val searchUrl = "https://www.dharitri.com/?s=${getCurrentDate()}+horoscope"
            val doc: Document =
                Jsoup.connect(searchUrl).get()
            DAILY_HOROSCOPE_URL =
                doc.select("section.content-area div.col-lg-6").first()?.select("a")
                    ?.first()?.attr("href") ?: ""
            getDailyData(DAILY_HOROSCOPE_URL)
        }
    }

    private fun getDailyData(url: String): List<Horoscope> {
        val doc: Document =
            Jsoup.connect(url).get()
        val doc2 = doc.select("div.p-t-20 div.row")
        return doc2.map { e ->
            Horoscope(
                rashiId = 1,
                rashi = e.select("div.col-md-2 strong").text(),
                details = e.select("div.col-md-10").text()
            )
        }
    }

}