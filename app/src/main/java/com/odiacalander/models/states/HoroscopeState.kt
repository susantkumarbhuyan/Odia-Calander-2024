package com.odiacalander.models.states

import com.odiacalander.models.Horoscope

sealed class HoroscopeState {
    class Success(val data: List<Horoscope>): HoroscopeState()
    class Failure(val msg: Throwable): HoroscopeState()
    data object Loading : HoroscopeState()
    data object Empty : HoroscopeState()
}