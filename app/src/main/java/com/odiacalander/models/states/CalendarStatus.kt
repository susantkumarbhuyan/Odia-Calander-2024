package com.odiacalander.models.states

import com.odiacalander.newcalender.core.NewMonth


sealed class CalendarStatus {
    class Success(val data: NewMonth): CalendarStatus()
    class Failure(val msg: Throwable): CalendarStatus()
    data object Loading : CalendarStatus()
    data object Empty : CalendarStatus()
}