package com.soleel.finanzas.feature.accounts.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.accounts.AccountsRoute

const val ACCOUNTS_ROUTE = "accounts"

fun NavController.navigateToAccounts(navOptions: NavOptions? = null) {
    this.navigate(ACCOUNTS_ROUTE, navOptions)
}

fun NavGraphBuilder.accountsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = ACCOUNTS_ROUTE,
        content = {
            AccountsRoute(
                finishApp = finishApp
            )
        }
    )
}