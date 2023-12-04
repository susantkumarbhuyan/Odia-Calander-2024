package com.odiacalander.newdataclasses

import com.odiacalander.entity.MonthDetailsEntity

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
    constructor(monthDetailsEntity: MonthDetailsEntity) : this(
        monthDetailsEntity.lang,
        monthDetailsEntity.name,
        monthDetailsEntity.year,
        monthDetailsEntity.month,
        monthDetailsEntity.festivals,
        monthDetailsEntity.govtHolidays,
        monthDetailsEntity.lunarDays,
        monthDetailsEntity.muhurata
    )
}
