package com.odiacalander.core.util

import androidx.compose.ui.graphics.Color
import com.odiacalander.R
import com.odiacalander.models.Week
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDate(): Int {
    val current = LocalDateTime.now()
    return current.dayOfMonth
}

fun getCurrentMonth(): Int {
    val current = LocalDateTime.now()
    current.month.name
    return current.month.value
}

fun getCurrentYear(): Int {
    val current = LocalDateTime.now()
    return current.year
}
fun getCurrentWeek(): Int {
    val current = LocalDateTime.now()

    return current.dayOfWeek.value
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

val localeDates = mapOf(
    1 to R.string.one,
    2 to R.string.two,
    3 to R.string.three,
    4 to R.string.four,
    5 to R.string.five,
    6 to R.string.six,
    7 to R.string.seven,
    8 to R.string.eight,
    9 to R.string.nine,
    10 to R.string.ten,
    11 to R.string.eleven,
    12 to R.string.twelve,
    13 to R.string.thirteen,
    14 to R.string.fourteen,
    15 to R.string.fifteen,
    16 to R.string.sixteen,
    17 to R.string.seventeen,
    18 to R.string.eighteen,
    19 to R.string.nineteen,
    20 to R.string.twenty,
    21 to R.string.twenty_one,
    22 to R.string.twenty_two,
    23 to R.string.twenty_three,
    24 to R.string.twenty_four,
    25 to R.string.twenty_five,
    26 to R.string.twenty_six,
    27 to R.string.twenty_seven,
    28 to R.string.twenty_eight,
    29 to R.string.twenty_nine,
    30 to R.string.thirty,
    31 to R.string.thirty_one
)

val localeYears = mapOf(
    2021 to R.string.year2023,
    2022 to R.string.year2023,
    2023 to R.string.year2023,
    2024 to R.string.year2024,
    2025 to R.string.year2025,
    2026 to R.string.year2026
)

val lunarDays = listOf("ଅମାବାସ୍ୟା", "ପୂର୍ଣିମା", "ଏକାଦଶୀ", "ଏକାଦଶୀ")

val fullWeeksNameOdia = mapOf(
    1 to "ସୋମବାର",
    2 to "ମଙ୍ଗଳବାର",
    3 to "ବୁଧବାର",
    4 to "ଗୁରୁବାର",
    5 to "ଶୁକ୍ରବାର",
    6 to "ସନିବାର",
    7 to "ରବିବାର"
)




