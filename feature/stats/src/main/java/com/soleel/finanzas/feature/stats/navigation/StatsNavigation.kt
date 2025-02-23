package com.soleel.finanzas.feature.stats.navigation

import android.content.Context
import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.stats.StatsRoute

const val statsRoute = "stats_route"

fun NavController.navigateToStats(navOptions: NavOptions? = null) {
    this.navigate(statsRoute, navOptions)
}

fun NavGraphBuilder.statsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = statsRoute,
        enterTransition = { fadeIn(initialAlpha = 1f) },
        exitTransition = { fadeOut(targetAlpha = 1f) },
        popEnterTransition = { fadeIn(initialAlpha = 1f) },
        popExitTransition = { fadeOut(targetAlpha = 1f) },
        content = {
            StatsRoute(
                finishApp = finishApp
            )
        }
    )
}