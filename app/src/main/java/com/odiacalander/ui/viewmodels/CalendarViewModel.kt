package com.odiacalander.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.states.CalendarStatus
import com.odiacalander.models.states.HoroscopeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {
    val response: MutableState<CalendarStatus> = mutableStateOf(CalendarStatus.Empty)

    init {

    }

    fun getMonthDetails(monthId: Int) = viewModelScope.launch {
        repository.getMonthDetails(monthId).onStart {
            response.value = CalendarStatus.Loading
        }.catch {
            response.value = CalendarStatus.Failure(it)
        }.collect {
            response.value = CalendarStatus.Success(it)
        }
    }
}