package com.soleel.finanzas.feature.createtransaction.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.createtransaction.CreateTransactionRoute

const val CREATE_TRANSACTION_ROUTE = "create_transaction"

fun NavController.navigateToCreateTransactionRoute(navOptions: NavOptions? = null) {
    this.navigate(CREATE_TRANSACTION_ROUTE, navOptions)
}

fun NavGraphBuilder.createTransactionScreen(
    onBackToPreviousView: () -> Unit
) {
    composable(
        route = CREATE_TRANSACTION_ROUTE,
        enterTransition = { fadeIn(initialAlpha = 1f) },
        exitTransition = { fadeOut(targetAlpha = 1f) },
        popEnterTransition = { fadeIn(initialAlpha = 1f) },
        popExitTransition = { fadeOut(targetAlpha = 1f) },
        content = {
            CreateTransactionRoute(
                onBackToPreviousView = onBackToPreviousView
            )
        }
    )
}