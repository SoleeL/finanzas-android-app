package com.soleel.finanzas.navigation

enum class TransactionsLevelDestination(
    val fullTitleText: String,
    val summaryTitle: String
) {
    ALL_TRANSACTIONS(
        fullTitleText = "Todas",
        summaryTitle = "T"
    ),

    DAILY_TRANSACTIONS(
        fullTitleText = "Dia",
        summaryTitle = "D"
    ),

    WEEKLY_TRANSACTIONS(
        fullTitleText = "Semana",
        summaryTitle = "S"
    ),

    MONTHLY_TRANSACTIONS(
        fullTitleText = "Mes",
        summaryTitle = "M"
    ),

    ANNUALLY_TRANSACTIONS(
        fullTitleText = "Año",
        summaryTitle = "A"
    );
}