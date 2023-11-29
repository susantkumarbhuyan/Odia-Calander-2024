package com.odiacalander.dataclasses

data class Date(
    val date: String = "",
    val indexNo: Int,
    val currentDateHolidays: List<String>,
    val isGovtHoliday: Boolean,
    val dayType: Int,
    val rashiphala: String
)
