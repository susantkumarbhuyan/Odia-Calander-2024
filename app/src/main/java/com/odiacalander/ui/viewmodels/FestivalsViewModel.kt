package com.odiacalander.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.getCurrentYear
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.states.CalendarImgState
import com.odiacalander.models.states.FestivalsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FestivalsViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {

    val response: MutableState<FestivalsState> = mutableStateOf(FestivalsState.Empty)

    init {
        getYearlyFestival(year = getCurrentYear(), langId = PreferenceUtil.getLanguageNumber())
    }

    private fun getYearlyFestival(year: Int, langId: Int) = viewModelScope.launch {
        repository.getYearlyFestival(year, langId).onStart {
            response.value = FestivalsState.Loading
        }.catch {
            response.value = FestivalsState.Failure(it)
        }.collect {
            response.value = FestivalsState.Success(it)
        }
    }
}