package com.odiacalander.core.util

import androidx.compose.ui.graphics.Color
import com.odiacalander.R
import com.odiacalander.models.Week
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd")
    return current.format(formatter)
}

fun getCurrentMonth(): Int {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MM")
    return current.format(formatter).toInt()
}

fun getCurrentYear(): Int {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("uuuu")
    return current.format(formatter).toInt()
}

fun getCurrentMonthName(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM")
    return current.format(formatter)
}

val localeMonths = mapOf(
    1 to R.string.January,
    2 to R.string.February,
    3 to R.string.March,
    4 to R.string.April,
    5 to R.string.May,
    6 to R.string.June,
    7 to R.string.July,
    8 to R.string.August,
    9 to R.string.September,
    10 to R.string.October,
    11 to R.string.November,
    12 to R.string.December

)

val weeks = listOf(
    Week(R.string.monday, Color(0xFF0766AD)),
    Week(R.string.tuesday, Color(0xFF4E9F3D)),
    Week(R.string.wednesday, Color(0xFF3F0071)),
    Week(R.string.thursday, Color(0xFFC84B31)),
    Week(R.string.friday, Color(0xFF39A7FF)),
    Week(R.string.saturday, Color(0xFFBE3144)),
    Week(R.string.sunday, Color(0xFFBE3144))
)

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

fun getMonthIndexId(month: Int = getCurrentMonth(), year: Int = getCurrentYear()): Int {
    return months.indexOf("${year}_${month}")
}

fun getDateOrdinalSuffix(date: Int): String {
    return when {
        date in 11..13 -> "$date" + "th"
        date % 10 == 1 -> "$date" + "st"
        date % 10 == 2 -> "$date" + "nd"
        date % 10 == 3 -> "$date" + "rd"
        else -> "$date" + "th"
    }
}




