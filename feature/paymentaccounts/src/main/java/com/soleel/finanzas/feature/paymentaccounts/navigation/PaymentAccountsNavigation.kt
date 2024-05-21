package com.soleel.finanzas.feature.paymentaccounts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.paymentaccounts.AccountsRoute

const val paymentAccountsRoute = "payment_accounts_route"

fun NavController.navigateToAccounts(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountsRoute, navOptions)
}

fun NavGraphBuilder.accountsScreen() {
    composable(
        route = paymentAccountsRoute,
        content = { AccountsRoute() }
    )
}