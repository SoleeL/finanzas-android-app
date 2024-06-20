package com.soleel.finanzas.feature.profile.navigation

import android.content.Context
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
        content = {
            ProfileRoute(
                finishApp = finishApp
            )
        }
    )
}