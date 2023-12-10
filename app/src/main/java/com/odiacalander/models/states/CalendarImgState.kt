package com.odiacalander.models.states

import com.odiacalander.models.Month

sealed class CalendarImgState {
    class Success(val data: List<Month>) : CalendarImgState()
    class Failure(val msg: Throwable) : CalendarImgState()
    data object Loading : CalendarImgState()
    data object Empty : CalendarImgState()
}