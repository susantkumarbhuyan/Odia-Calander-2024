package com.odiacalander.newcalender.compose


import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.odiacalander.newcalender.compose.CalendarDefaults.flingBehavior
import com.odiacalander.newcalender.core.CalendarDay
import com.odiacalander.newcalender.core.CalendarMonth

/**
 * A horizontally scrolling calendar.
 *
 * @param modifier the modifier to apply to this calendar.
 * @param state the state object to be used to control or observe the calendar's properties.
 * Examples: `startMonth`, `endMonth`, `firstDayOfWeek`, `firstVisibleMonth`, `outDateStyle`.
 * @param calendarScrollPaged the scrolling behavior of the calendar. When `true`, the calendar will
 * snap to the nearest month after a scroll or swipe action. When `false`, the calendar scrolls normally.
 * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
 * is allowed. You can still scroll programmatically using the state even when it is disabled.
 * @param contentPadding a padding around the whole calendar. This will add padding for the
 * content after it has been clipped, which is not possible via [modifier] param. You can use it
 * to add a padding before the first month or after the last one. If you want to add a spacing
 * between each month use the [monthContainer] composable.
 * @param contentHeightMode Determines how the height of the day content is calculated.
 * @param dayContent a composable block which describes the day content.
 * @param monthHeader a composable block which describes the month header content. The header is
 * placed above each month on the calendar.
 * @param monthBody a composable block which describes the month body content. This is the container
 * where all the month days are placed, excluding the header and footer. This is useful if you
 * want to customize the day container, for example, with a background color or other effects.
 * The actual body content is provided in the block and must be called after your desired
 * customisations are rendered.
 * @param monthFooter a composable block which describes the month footer content. The footer is
 * placed below each month on the calendar.
 * @param monthContainer a composable block which describes the entire month content. This is the
 * container where all the month contents are placed (header => days => footer). This is useful if
 * you want to customize the month container, for example, with a background color or other effects.
 * The actual container content is provided in the block and must be called after your desired
 * customisations are rendered.
 */
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState = rememberCalendarState(),
    calendarScrollPaged: Boolean = true,
    userScrollEnabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    contentHeightMode: ContentHeightMode = ContentHeightMode.Wrap,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)? = null,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)? = null,

) = Calendar(
    modifier = modifier,
    state = state,
    calendarScrollPaged = calendarScrollPaged,
    userScrollEnabled = userScrollEnabled,
    contentHeightMode = contentHeightMode,
    dayContent = dayContent,
    monthHeader = monthHeader,
    monthBody = monthBody,
    monthFooter = monthFooter,
    monthContainer = monthContainer,
    contentPadding = contentPadding,

)


@Composable
private fun Calendar(
    modifier: Modifier,
    state: CalendarState,
    calendarScrollPaged: Boolean,
    userScrollEnabled: Boolean,
    contentPadding: PaddingValues,
    contentHeightMode: ContentHeightMode,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)?,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)?,
) {
    LazyRow(
        modifier = modifier,
        state = state.listState,
        flingBehavior = flingBehavior(calendarScrollPaged, state.listState),
        userScrollEnabled = userScrollEnabled,
        contentPadding = contentPadding,
    ) {

        CalendarMonths(
            monthCount = state.monthIndexCount,
            monthData = { offset -> state.store[offset] },
            contentHeightMode = contentHeightMode,
            dayContent = dayContent,
            monthHeader = monthHeader,
            monthBody = monthBody,
            monthFooter = monthFooter,
            monthContainer = monthContainer,
        )
    }
}
