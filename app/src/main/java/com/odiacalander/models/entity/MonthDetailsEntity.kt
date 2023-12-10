package com.odiacalander.models.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.odiacalander.newcalender.core.NewMonth

/**
 * Represents a day on the calendar.
 *
 * @param id id of MontDetails Structure -> langID,MonthID,Year ex: 2122023 -> en, dec, 2023.
 * @param lang  "or"-1, "en_US"-2, "hi"-3.
 */
@Entity(tableName = "monthDetails")
@Keep
data class MonthDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val lang: String,
    val langId: Int,
    val name: String,
    val year: Int,
    val month: Int,
    val festivals: Map<String, List<String>>,
    val govtHolidays: Map<String, String>,
    val lunarDays: Map<String, Int>,
    val muhurata: Map<String, String>,
    val highlightDays : List<Int>
) {
    fun toNewMonth(): NewMonth {
        return NewMonth(
            lang = lang,
            langId = langId,
            name = name,
            year = year,
            month = month,
            festivals = festivals,
            govtHolidays = govtHolidays,
            lunarDays = lunarDays,
            muhurata = muhurata,
            highlightDays=highlightDays
        )
    }
}
