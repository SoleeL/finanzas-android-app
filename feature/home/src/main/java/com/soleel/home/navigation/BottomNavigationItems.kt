package com.soleel.home.navigation

import com.soleel.finanzas.core.ui.R


sealed class HomeBottomNavigationItems(var title: String, var icon: Int, var screenRoute: String) {
    data object Init : HomeBottomNavigationItems("Inicio", R.drawable.ic_home, "init")
    data object Stats : HomeBottomNavigationItems("Estadística", R.drawable.ic_stats, "stats")
    data object Accounts : HomeBottomNavigationItems("Cuentas", R.drawable.ic_accounts, "accounts")
    data object Profile : HomeBottomNavigationItems("Perfil", R.drawable.ic_profile, "profile")
    data object Add : HomeBottomNavigationItems("Añadir", R.drawable.ic_add_circle, "add")
}
