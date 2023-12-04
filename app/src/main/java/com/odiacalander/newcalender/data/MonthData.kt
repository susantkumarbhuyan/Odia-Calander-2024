package com.odiacalander.newcalender.data


import android.content.Context
import com.odiacalander.util.getCurrentDate
import com.odiacalander.util.getCurrentMonth
import com.odiacalander.util.getCurrentYear
import com.odiacalander.newcalender.core.CalendarDay
import com.odiacalander.newcalender.core.CalendarMonth
import com.odiacalander.newcalender.core.DayPosition
import com.odiacalander.newcalender.core.OutDateStyle
import com.odiacalander.newcalender.core.atStartOfMonth
import com.odiacalander.newcalender.core.nextMonth
import com.odiacalander.newcalender.core.previousMonth
import com.odiacalander.newcalender.core.yearMonth
import com.odiacalander.newdataclasses.NewDayData
import com.odiacalander.util.CalendarManager
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.ChronoUnit

data class MonthData internal constructor(
    private val month: YearMonth,
    private val inDays: Int,
    private val outDays: Int,
    private val context: Context
) {
    private val monthData = CalendarManager.loadMonthDetailsFromDB(context)
    private val totalDays = inDays + month.lengthOfMonth() + outDays

    private val firstDay = month.atStartOfMonth().minusDays(inDays.toLong())

    private val rows = (0 until totalDays).chunked(7)

    private val previousMonth = month.previousMonth

    private val nextMonth = month.nextMonth

    val calendarMonth =
        CalendarMonth(
            month,
            rows.map { week -> week.map { dayOffset -> getDay(dayOffset) } },
            monthData
        )

    private fun getDay(dayOffset: Int): CalendarDay {
        val date = firstDay.plusDays(dayOffset.toLong())
        val position = when (date.yearMonth) {
            month -> DayPosition.MonthDate
            previousMonth -> DayPosition.InDate
            nextMonth -> DayPosition.OutDate
            else -> throw IllegalArgumentException("Invalid date: $date in month: $month")
        }
        val cDate = date.dayOfMonth.toString()
        val dayData = NewDayData(
            monthData.festivals[cDate].orEmpty(),
            isGovtHoliday = monthData.govtHolidays.containsKey(cDate) ,
            monthData.lunarDays[cDate],
            isCurrentData = (date.year == getCurrentYear() && date.yearMonth.monthValue == getCurrentMonth() && cDate == getCurrentDate()),
            isSunday =  arrayOf("SUNDAY").contains(date.dayOfWeek.name)
        )
        return CalendarDay(date, position, dayData)
    }
}

fun getCalendarMonthData(
    startMonth: YearMonth,
    offset: Int,
    firstDayOfWeek: DayOfWeek,
    outDateStyle: OutDateStyle,
    context: Context
): MonthData {
    val month = startMonth.plusMonths(offset.toLong())
    val firstDay = month.atStartOfMonth()
    val inDays = firstDayOfWeek.daysUntil(firstDay.dayOfWeek)
    val outDays = (inDays + month.lengthOfMonth()).let { inAndMonthDays ->
        val endOfRowDays = if (inAndMonthDays % 7 != 0) 7 - (inAndMonthDays % 7) else 0
        val endOfGridDays = if (outDateStyle == OutDateStyle.EndOfRow) 0 else run {
            val weeksInMonth = (inAndMonthDays + endOfRowDays) / 7
            return@run (6 - weeksInMonth) * 7
        }
        return@let endOfRowDays + endOfGridDays
    }
    return MonthData(month, inDays, outDays, context)
}


fun getMonthIndex(startMonth: YearMonth, targetMonth: YearMonth): Int {
    return ChronoUnit.MONTHS.between(startMonth, targetMonth).toInt()
}

fun getMonthIndicesCount(startMonth: YearMonth, endMonth: YearMonth): Int {
    // Add one to include the start month itself!
    return getMonthIndex(startMonth, endMonth) + 1
}
