package com.soleel.finanzas.feature.createaccount.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.createaccount.CreateAccountRoute


const val CREATE_ACCOUNT_ROUTE = "create_account"

fun NavController.navigateToCreateAccountRoute(navOptions: NavOptions? = null) {
    this.navigate(CREATE_ACCOUNT_ROUTE, navOptions)
}

fun NavGraphBuilder.createAccountScreen(
    onBackToPreviousView: () -> Unit
) {
    composable(
        route = CREATE_ACCOUNT_ROUTE,
        enterTransition = { fadeIn(initialAlpha = 1f) },
        exitTransition = { fadeOut(targetAlpha = 1f) },
        popEnterTransition = { fadeIn(initialAlpha = 1f) },
        popExitTransition = { fadeOut(targetAlpha = 1f) },
        content = {
            CreateAccountRoute(
                onBackToPreviousView = onBackToPreviousView
            )
        }
    )
}