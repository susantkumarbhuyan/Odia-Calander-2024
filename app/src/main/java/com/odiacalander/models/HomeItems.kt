package com.odiacalander.models

import android.net.Uri
import com.odiacalander.R

sealed class HomeItems(val title: Int, val image: String, val route: String) {
    data object CalendarImgs :
        HomeItems(
            title = 0,
            image = "",
            route = "calander_imgs"
        )

    data object Calendar :
        HomeItems(
            title = R.string.Calendar,
            image = "android.resource://com.odiacalander/${R.drawable.calendar}",
            route = "calender"
        )

    data object Horoscope :
        HomeItems(
            title = R.string.Horoscope,
            image = "https://cdntc.mpanchang.com/mpnc/images/sun_sign.png",
            route = "horoscope"
        )

    data object Muhurat :
        HomeItems(
            title = R.string.Muhurat,
            image = "https://cdntc.mpanchang.com/mpnc/images/sun_sign.png",
            route = "muhurat"
        )

    data object GovtHolidays :
        HomeItems(
            title = R.string.GovtHolidays,
            image = "https://cdntc.mpanchang.com/mpnc/images/sun_sign.png",
            route = "govtHolidays"
        )

    data object Festivals :
        HomeItems(
            title = R.string.Festivals,
            image = "https://cdntc.mpanchang.com/mpnc/images/diwali_big.png",
            route = "festivals"
        )
}

fun getHomeItems(): List<HomeItems> {
    return listOf(
        HomeItems.CalendarImgs,
        HomeItems.Calendar,
        HomeItems.Horoscope,
        HomeItems.Muhurat,
        HomeItems.GovtHolidays,
        HomeItems.Festivals
    )
}
