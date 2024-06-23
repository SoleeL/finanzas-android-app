package com.soleel.finanzas.core.ui.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
object DateFormats {
    val allTransactionFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dailyTransactionFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val weeklyTransactionFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val monthlyTransactionFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())
    val annuallyTransactionFormat = SimpleDateFormat("yyyy", Locale.getDefault())
}

fun getAllTransactionStringDate(date: Long): String {
    return DateFormats.allTransactionFormat.format(Date(date))
}

fun getDailyTransactionStringDate(date: Long): String {
    return DateFormats.dailyTransactionFormat.format(Date(date))
}

fun getWeeklyTransactionStringDate(date: Long): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = date
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
    return DateFormats.weeklyTransactionFormat.format(calendar.time)
}

fun getMonthlyTransactionStringDate(date: Long): String {
    return DateFormats.monthlyTransactionFormat.format(Date(date))
}

fun getAnnuallyTransactionStringDate(date: Long): String {
    return DateFormats.annuallyTransactionFormat.format(Date(date))
}