package com.odiacalander.newcalender.core

import com.odiacalander.data.db.entity.MonthDetailsEntity

data class NewMonth(
    val lang: String,
    val name: String,
    val year: Int,
    val month: Int,
    val festivals: Map<String, List<String>>,
    val govtHolidays: Map<String, String>,
    val lunarDays: Map<String, Int>,
    val muhurata: Map<String, String>
){
    constructor() : this(
       "",
       "",
        0,
        0,
        emptyMap(),
        emptyMap(),
        emptyMap(),
        emptyMap()
    )
}
