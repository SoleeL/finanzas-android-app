package com.soleel.finanzas.feature.transactions.navigation.destination

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
    ),

    ANNUALLY(
        title = "Año",
        summaryTitle = "A"
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