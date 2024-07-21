package com.soleel.finanzas.feature.transactions.navigation.destination

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination.Companion.lowercase

enum class TransactionsLevelDestination(
    val title: String,
    val summaryTitle: String
) {
    ALL(
        title = "Todas",
        summaryTitle = "T"
    ),

    DAILY(
        title = "Dia",
        summaryTitle = "D"
    ),

    WEEKLY(
        title = "Semana",
        summaryTitle = "S"
    ),

    MONTHLY(
        title = "Mes",
        summaryTitle = "M"
    );

    companion object {
        fun fromName(title: String): TransactionsLevelDestination {
            val transactionsLevelDestination: TransactionsLevelDestination? = TransactionsLevelDestination
                .entries
                .find(predicate = { it.name == title.uppercase() })
            return transactionsLevelDestination ?: ALL
        }

        fun TransactionsLevelDestination.lowercase(): String {
            return this.toString().lowercase()
        }
    }
}

fun NavDestination?.isTransactionsLevelDestination(): Boolean {
    val destinationRute = this?.route ?: return false
    return TransactionsLevelDestination.entries.any( predicate =  { destinationRute.contains(it.lowercase(), ignoreCase = true) })
}