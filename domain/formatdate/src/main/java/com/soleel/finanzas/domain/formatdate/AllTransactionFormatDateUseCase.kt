package com.soleel.finanzas.domain.formatdate

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class AllTransactionFormatDateUseCase {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

        operator fun invoke(localDateTime: LocalDateTime): String {
            return formatter.format(localDateTime)
        }
    }
}