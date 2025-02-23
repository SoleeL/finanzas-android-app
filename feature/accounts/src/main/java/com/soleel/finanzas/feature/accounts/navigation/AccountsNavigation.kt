package com.soleel.finanzas.feature.accounts.navigation

import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        enterTransition = { fadeIn(initialAlpha = 1f) },
        exitTransition = { fadeOut(targetAlpha = 1f) },
        popEnterTransition = { fadeIn(initialAlpha = 1f) },
        popExitTransition = { fadeOut(targetAlpha = 1f) },
        content = {
            AccountsRoute(
                finishApp = finishApp
            )
        }
    )
}