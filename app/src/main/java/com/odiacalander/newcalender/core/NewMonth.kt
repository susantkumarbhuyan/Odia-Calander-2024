package com.odiacalander.newcalender.core

import com.odiacalander.models.entity.MonthDetailsEntity

data class NewMonth(
    val lang: String,
    val name: String,
    val langId: Int,
    val year: Int,
    val month: Int,
    val festivals: Map<String, List<String>>,
    val govtHolidays: Map<String, String>,
    val lunarDays: Map<String, Int>,
    val muhurata: Map<String, String>,
    val highlightDays: List<Int>
) {
    constructor() : this(
        "",
        "",
        0,
        0,
        0,
        emptyMap(),
        emptyMap(),
        emptyMap(),
        emptyMap(),
        emptyList()
    )
}
