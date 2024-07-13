package com.soleel.finanzas.domain.transactions.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale

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

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Date.getWeekOfMonth(): Int {
    val localDate = this.toLocalDate()
    val weekFields = WeekFields.of(Locale("es"))
    return localDate.get(weekFields.weekOfMonth())
}

fun Date.getMonthName(): String {
    val localDate = this.toLocalDate()
    return localDate.month.getDisplayName(TextStyle.FULL, Locale("es"))
}

fun Date.toIncomeNameTransactionDay(): String {
    val localDate: LocalDate = this.toLocalDate()
    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es"))
    return "Ingresos del $dayOfWeek"
}

fun Date.toExpenditureNameTransactionDay(): String {
    val localDate: LocalDate = this.toLocalDate()
    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es"))
    return "Gastos del $dayOfWeek"
}

fun Date.toIncomeNameTransactionWeek(): String {
    val weekOfMonth = this.getWeekOfMonth()
    val monthName = this.getMonthName()
    return "Ingresos de la semana $weekOfMonth de $monthName"
}

fun Date.toExpenditureNameTransactionWeek(): String {
    val weekOfMonth = this.getWeekOfMonth()
    val monthName = this.getMonthName()
    return "Gastos de la semana $weekOfMonth de $monthName"
}

fun Date.toIncomeNameTransactionMonth(): String {
    val monthName = this.getMonthName()
    return "Ingresos de $monthName"
}

fun Date.toExpenditureNameTransactionMonth(): String {
    val monthName = this.getMonthName()
    return "Gastos de $monthName"
}

fun Date.toIncomeNameTransactionYear(): String {
    return "Ingresos de ${this.toLocalDate().year}"
}

fun Date.toExpenditureNameTransactionYear(): String {
    return "Gastos de ${this.toLocalDate().year}"
}