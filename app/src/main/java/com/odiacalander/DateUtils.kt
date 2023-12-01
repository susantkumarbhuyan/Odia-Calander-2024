package com.odiacalander

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.odiacalander.dataclasses.Date
import com.odiacalander.dataclasses.HomeItem
import com.odiacalander.dataclasses.Month
import com.odiacalander.dataclasses.Week
import com.odiacalander.ui.theme.azure
import com.odiacalander.ui.theme.*
import org.json.JSONObject
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

fun getMonths(): List<Month> {
    return listOf(
        Month(month = "January", year = 2024, monthId = 1, image = R.drawable.january_2024),
        Month(month = "February", year = 2024, monthId = 2, image = R.drawable.february_2024),
        Month(month = "March", year = 2024, monthId = 3, image = R.drawable.march_2024),
        Month(month = "April", year = 2024, monthId = 4, image = R.drawable.may_2024),
        Month(month = "May", year = 2024, monthId = 5, image = R.drawable.april_2024),
        Month(month = "June", year = 2024, monthId = 6, image = R.drawable.june_2024),
        Month(month = "July", year = 2024, monthId = 7, image = R.drawable.july_2024),
        Month(month = "August", year = 2024, monthId = 8, image = R.drawable.august_2024),
        Month(month = "September", year = 2024, monthId = 9, image = R.drawable.september_2024),
        Month(month = "October", year = 2024, monthId = 10, image = R.drawable.october_2024),
        Month(month = "November", year = 2024, monthId = 11, image = R.drawable.november_2024),
        Month(month = "December", year = 2024, monthId = 12, image = R.drawable.december_2024)
    )
}

val weeks = listOf(
    Week("Sun", Color(0xFFBE3144)),
    Week("Mon", Color(0xFF0766AD)),
    Week("Tue", Color(0xFF4E9F3D)),
    Week("Wed", Color(0xFF3F0071)),
    Week("Thu", Color(0xFFC84B31)),
    Week("Fri", Color(0xFF39A7FF)),
    Week("Sat", Color(0xFFBE3144))
)







