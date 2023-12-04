package com.odiacalander.newcalender.core



import androidx.compose.runtime.Immutable
import com.odiacalander.newdataclasses.NewDayData
import java.io.Serializable
import java.time.LocalDate

/**
 * Represents a day on the calendar.
 *
 * @param date the date for this day.
 * @param position the [DayPosition] for this day.
 */
@Immutable
data class CalendarDay(val date: LocalDate, val position: DayPosition, val dayData : NewDayData) : Serializable
