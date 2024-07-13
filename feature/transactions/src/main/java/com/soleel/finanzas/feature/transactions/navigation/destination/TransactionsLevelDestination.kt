package com.soleel.finanzas.feature.transactions.navigation.destination

enum class TransactionsLevelDestination(
    val title: String,
    val summaryTitle: String
) {
    ALL_TRANSACTIONS(
        title = "Todas",
        summaryTitle = "T"
    ),

    DAILY_TRANSACTIONS(
        title = "Dia",
        summaryTitle = "D"
    ),

    WEEKLY_TRANSACTIONS(
        title = "Semana",
        summaryTitle = "S"
    ),

    MONTHLY_TRANSACTIONS(
        title = "Mes",
        summaryTitle = "M"
    ),

    ANNUALLY_TRANSACTIONS(
        title = "Año",
        summaryTitle = "A"
    );

    companion object {
        fun fromTitle(title: String): TransactionsLevelDestination {
            val transactionsLevelDestination: TransactionsLevelDestination? = TransactionsLevelDestination
                .entries
                .find(predicate = { it.title == title })
            return transactionsLevelDestination ?: DAILY_TRANSACTIONS
        }
    }
}