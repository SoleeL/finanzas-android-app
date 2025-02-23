package com.soleel.finanzas.domain.transactions.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale


fun LocalDateTime.toDayDate(): LocalDate {
    return this.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDateTime.toWeekDate(): LocalDate {
    val localDate = this.atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
}

fun LocalDateTime.toMonthDate(): LocalDate {
    val localDate = this.atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.firstDayOfMonth())
}

fun LocalDateTime.toYearDate(): LocalDate {
    val localDate = this.atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.firstDayOfYear())
}

fun LocalDate.toNameDaysOfWeek(): String {
    val monthName = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "Semana del ${this.dayOfMonth} de $monthName del ${this.year}"
}

fun LocalDate.toNameWeekOfMonth(): String {
    val monthName = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "$monthName del ${this.year}"
}

fun LocalDate.toNameMonthOfYear(): String {
    return "${this.year}"
}

fun LocalDate.toNameTransactionDay(): String {
    val dayOfWeek = this.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "${this.dayOfMonth} - $dayOfWeek"
}

fun LocalDate.toNameTransactionWeek(): String {
    val weekDay = this.dayOfMonth
    val monthName = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val weekFields = WeekFields.of(Locale.getDefault())
    val weekOfMonth = this.get(weekFields.weekOfMonth())

    return "$weekDay de $monthName - ${weekOfMonth.toOrdinal()} semana"
}

fun Int.toOrdinal(): String {
    return when (this) {
        1 -> "1era"
        2 -> "2da"
        3 -> "3era"
        4 -> "4ta"
        5 -> "5ta"
        else -> "$this"
    }
}

fun LocalDate.toNameTransactionMonth(): String {
    val monthName = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return monthName
}