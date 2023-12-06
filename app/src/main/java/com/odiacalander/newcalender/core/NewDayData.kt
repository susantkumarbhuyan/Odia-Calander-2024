package com.odiacalander.newcalender.core

data class NewDayData(val festivals: List<String>?, val isGovtHoliday: Boolean = false, val lunarDayType: Int?, val isCurrentData : Boolean, val isSunday : Boolean )
