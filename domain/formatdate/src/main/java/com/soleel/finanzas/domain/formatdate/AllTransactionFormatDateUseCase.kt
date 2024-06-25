package com.soleel.finanzas.domain.formatdate

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AllTransactionFormatDateUseCase {
    companion object {
        private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        operator fun invoke(date: Date): String {
            return formatter.format(date)
        }
    }

}