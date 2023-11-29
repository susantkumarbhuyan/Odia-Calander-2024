package com.odiacalander.dataclasses

data class MonthEntity(
    val name: String,
    val monthId: Int,
    val holidays: List<String>,
    val dates: List<Date>
)
