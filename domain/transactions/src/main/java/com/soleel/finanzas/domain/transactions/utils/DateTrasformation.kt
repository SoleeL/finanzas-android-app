package com.soleel.finanzas.domain.transactions.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale

fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.toDayDate(): Date {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toDate()
}

fun Date.toWeekDate(): Date {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toDate()
}

fun Date.toMonthDate(): Date {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.firstDayOfMonth()).toDate()
}

fun Date.toYearDate(): Date {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.with(TemporalAdjusters.firstDayOfYear()).toDate()
}

fun Date.toNameDaysOfWeek(): String {
    val localDate = this.toLocalDate()
    val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "Semana del ${localDate.dayOfMonth} de $monthName del ${localDate.year}"
}

fun Date.toNameWeekOfMonth(): String {
    val localDate = this.toLocalDate()
    val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "$monthName del ${localDate.year}"
}

fun Date.toNameMonthOfYear(): String {
    return "${this.toLocalDate().year}"
}

fun Date.toNameTransactionDay(): String {
    val localDate: LocalDate = this.toLocalDate()
    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return "${localDate.dayOfMonth} - $dayOfWeek"
}

fun Date.toNameTransactionWeek(): String {
    val localDate = this.toLocalDate()

    val weekDay = localDate.dayOfMonth
    val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val weekFields = WeekFields.of(Locale.getDefault())
    val weekOfMonth = localDate.get(weekFields.weekOfMonth())

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

fun Date.toNameTransactionMonth(): String {
    val localDate = this.toLocalDate()
    val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return monthName
}

fun Date.toIncomeNameTransactionYear(): String {
    return "Ingresos de ${this.toLocalDate().year}"
}

fun Date.toExpenditureNameTransactionYear(): String {
    return "Gastos de ${this.toLocalDate().year}"
}