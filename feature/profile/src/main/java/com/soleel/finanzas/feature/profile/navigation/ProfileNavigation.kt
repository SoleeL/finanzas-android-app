package com.soleel.finanzas.feature.profile.navigation

import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.finanzas.feature.profile.ProfileRoute


const val profileRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = profileRoute,
        enterTransition = { fadeIn(initialAlpha = 1f) },
        exitTransition = { fadeOut(targetAlpha = 1f) },
        popEnterTransition = { fadeIn(initialAlpha = 1f) },
        popExitTransition = { fadeOut(targetAlpha = 1f) },
        content = {
            ProfileRoute(
                finishApp = finishApp
            )
        }
    )
}