package com.soleel.finanzas.domain.formatdate

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AllTransactionsGroupDateUseCase {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'del' yyyy", Locale.getDefault())

        operator fun invoke(localDate: LocalDate): String {
            return formatter.format(localDate)
        }
    }
}