package com.soleel.finanzas.domain.formatdate

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AllTransactionsGroupDateUseCase {
    companion object {
        private val formatter = SimpleDateFormat("EEEE, d 'de' MMMM 'del' yyyy", Locale.getDefault())

        operator fun invoke(date: Date): String {
            return formatter.format(date)
        }
    }
}