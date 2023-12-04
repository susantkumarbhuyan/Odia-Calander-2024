package com.odiacalander.newdataclasses

data class NewDayData(val festivals: List<String>?, val isGovtHoliday: Boolean = false, val lunarDayType: Int?, val isCurrentData : Boolean, val isSunday : Boolean )
