package com.odiacalander.dataclasses

import com.odiacalander.R

sealed class HomeItems(val title: String, val image: Int, val route: String) {
    data object CalanderImgs :
        HomeItems(title = "CalanderImgs", image = 0, route = "calander_imgs")
    data object Calander :
        HomeItems(title = "Calander", image = R.drawable.calendar, route = "calander")
    data object Horoscop :
        HomeItems(title = "Horoscop", image = R.drawable.calendar, route = "horoscop")

    data object Muhurtha :
        HomeItems(title = "Muhurtha", image = R.drawable.calendar, route = "muhurth")

    data object GovtHolidays :
        HomeItems(title = "Govt Holidays", image = R.drawable.calendar, route = "govtHolidays")

    data object Festivals :
        HomeItems(title = "Festivals", image = R.drawable.calendar, route = "festivals")
}

fun getHomeItems(): List<HomeItems> {
    return listOf(
        HomeItems.CalanderImgs,
        HomeItems.Calander,
        HomeItems.Horoscop,
        HomeItems.Muhurtha,
        HomeItems.GovtHolidays,
        HomeItems.Festivals
    )
}
