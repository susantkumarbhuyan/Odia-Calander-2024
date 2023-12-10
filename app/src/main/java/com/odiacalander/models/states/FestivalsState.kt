package com.odiacalander.models.states

import com.odiacalander.models.Month
import com.odiacalander.models.entity.MonthDetailsEntity
import com.odiacalander.newcalender.core.NewMonth

sealed class FestivalsState {
    class Success(val data: List<MonthDetailsEntity>) : FestivalsState()
    class Failure(val msg: Throwable) : FestivalsState()
    data object Loading : FestivalsState()
    data object Empty : FestivalsState()
}