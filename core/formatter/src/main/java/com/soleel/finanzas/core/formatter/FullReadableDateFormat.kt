package com.soleel.finanzas.core.formatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class FullReadableDateFormat {
    companion object {
        private val formatterWithoutYear = DateTimeFormatter.ofPattern(
            "d 'de' MMMM",
            Locale.getDefault())

        private val formatter = DateTimeFormatter.ofPattern(
            "d 'de' MMMM 'del' yyyy",
            Locale.getDefault())

        operator fun invoke(localDate: LocalDate): String {
            if (localDate.year == LocalDate.now().year) {
                return formatterWithoutYear.format(localDate)
            }
            return formatter.format(localDate)
        }
    }
}