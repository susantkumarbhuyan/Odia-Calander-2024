package com.odiacalander.newcalender.core


import androidx.compose.runtime.Immutable
import com.odiacalander.core.util.getCurrentDate
import com.odiacalander.core.util.getCurrentMonth
import com.odiacalander.core.util.getCurrentYear
import java.io.Serializable
import java.time.LocalDate

/**
 * Represents a day on the calendar.
 *
 * @param date the date for this day.
 * @param position the [DayPosition] for this day.
 */
@Immutable
data class CalendarDay(val date: LocalDate, val position: DayPosition, val dayData: NewDayData?) :
    Serializable {
    fun copy(newMonth: NewMonth): CalendarDay {
        val cDate = date.dayOfMonth.toString()
        val dayData = NewDayData(
            newMonth.festivals[cDate].orEmpty(),
            isGovtHoliday = newMonth.govtHolidays.containsKey(cDate),
            newMonth.lunarDays[cDate],
            isCurrentData = (date.year == getCurrentYear() && date.yearMonth.monthValue == getCurrentMonth() && cDate == getCurrentDate()),
            isSunday = arrayOf("SUNDAY").contains(date.dayOfWeek.name)
        )
        return CalendarDay(
            date = this.date,
            position = this.position,
            dayData = dayData
        )
    }
}
