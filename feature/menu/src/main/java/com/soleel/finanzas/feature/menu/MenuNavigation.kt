package com.soleel.finanzas.feature.menu

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.data.preferences.AppPreferences
import kotlinx.serialization.Serializable

@Serializable
object MenuGraph

@Serializable
object Menu

fun NavGraphBuilder.menuNavigationGraph(
    appPreferences: AppPreferences
) {
    navigation<MenuGraph>(startDestination = Menu) {
        composable<Menu> {
            MenuScreen()
        }
    }
}