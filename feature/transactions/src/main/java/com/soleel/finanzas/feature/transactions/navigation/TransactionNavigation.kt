package com.soleel.finanzas.feature.transactions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.transactions.HomeRoute


const val homeRoute = "home_route"

fun NavController.backToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = homeRoute,
        content = { HomeRoute() }
    )
}