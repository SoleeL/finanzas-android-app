package com.soleel.finanzas.feature.accounts.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.accounts.AccountsRoute

const val accountsRoute = "_accounts_route"

fun NavController.navigateToAccounts(navOptions: NavOptions? = null) {
    this.navigate(accountsRoute, navOptions)
}

fun NavGraphBuilder.accountsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = accountsRoute,
        content = {
            AccountsRoute(
                finishApp = finishApp
            )
        }
    )
}