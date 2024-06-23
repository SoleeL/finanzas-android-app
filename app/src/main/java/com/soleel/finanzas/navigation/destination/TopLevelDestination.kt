package com.soleel.finanzas.navigation.destination

import com.soleel.finanzas.core.ui.R

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: String,
    val titleTextId: String
) {
    TRANSACTIONS(
        selectedIcon = R.drawable.ic_add_transaction,
        unselectedIcon = R.drawable.ic_add_transaction,
        iconTextId = "Transaction",
        titleTextId = "Transacciones",
    ),

    PAYMENT_ACCOUNTS(
        selectedIcon = R.drawable.ic_accounts,
        unselectedIcon = R.drawable.ic_accounts,
        iconTextId = "Accounts",
        titleTextId = "Cuentas",
    ),

    STATS(
        selectedIcon = R.drawable.ic_stats,
        unselectedIcon = R.drawable.ic_stats,
        iconTextId = "Stats",
        titleTextId = "Estadisticas",
    ),

    PROFILE(
        selectedIcon = R.drawable.ic_profile,
        unselectedIcon = R.drawable.ic_profile,
        iconTextId = "Profile",
        titleTextId = "Perfil",
    );
}
