package com.kizitonwose.calendar.compose

import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.minusDays
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusDays
import com.kizitonwose.calendar.data.VisibleItemState
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeekCalendarStateTests {
    @Test
    fun startDateUpdateIsReflectedInTheState() {
        val now = LocalDate.now()
        val updatedStartDate = now.minusDays(7)
        val state = createState(
            startDate = now,
            endDate = now,
        )

        assertTrue(state.store[0].days.map { it.date }.contains(now))

        state.startDate = updatedStartDate

        assertTrue(state.store[0].days.map { it.date }.contains(updatedStartDate))
    }

    @Test
    fun endDateUpdateIsReflectedInTheState() {
        val now = LocalDate.now()
        val updatedEndDate = now.plusDays(7)
        val state = createState(
            startDate = now,
            endDate = now,
        )

        assertTrue(state.store[0].days.map { it.date }.contains(now))

        state.endDate = updatedEndDate

        assertTrue(state.store[1].days.map { it.date }.contains(updatedEndDate))
    }

    @Test
    fun firstDayOfTheWeekUpdateIsReflectedInTheState() {
        val firstDayOfWeek = LocalDate.now().dayOfWeek

        val state = createState(firstDayOfWeek = firstDayOfWeek)

        assertEquals(state.store[0].days.first().date.dayOfWeek, firstDayOfWeek)

        do {
            val updatedFirstDayOfWeek = state.firstDayOfWeek.plusDays(1)
            state.firstDayOfWeek = updatedFirstDayOfWeek

            assertEquals(state.store[0].days.first().date.dayOfWeek, updatedFirstDayOfWeek)
        } while (firstDayOfWeek != state.firstDayOfWeek)
    }

    private fun createState(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = startDate,
        firstVisibleWeekDate: LocalDate = startDate,
        firstDayOfWeek: DayOfWeek = firstDayOfWeekFromLocale(),
        visibleItemState: VisibleItemState = VisibleItemState(),
    ) = WeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = firstVisibleWeekDate,
        firstDayOfWeek = firstDayOfWeek,
        visibleItemState = visibleItemState,
    )
}
