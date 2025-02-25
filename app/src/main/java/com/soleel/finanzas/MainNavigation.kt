package com.soleel.finanzas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.data.preferences.AppPreferences
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.configuration.configurationNavigationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.home.MenuScreen
import com.soleel.finanzas.feature.home.homeNavigationGraph
import com.soleel.finanzas.feature.login.LoginGraph
import com.soleel.finanzas.feature.login.loginNavigationGraph
import kotlinx.serialization.Serializable


@Serializable
object MenuGraph

@Serializable
object AddGraph // Flujo invocado por la calculadora para ingresar una transaccion

@Composable
fun FinanzasNavigationGraph(
    appPreferences: AppPreferences
) {
    val navHostController: NavHostController = rememberNavController()

    val isLoggedIn: Boolean = appPreferences.getAuthToken()
        .collectAsState(initial = null).value != null
    val isAppConfigured: Boolean = appPreferences.getConfiguration()
        .collectAsState(initial = null).value != null

    val startDestination: Any

    if (!isLoggedIn) {
        startDestination = LoginGraph
    } else {
        if (!isAppConfigured) {
            startDestination = ConfigurationGraph
        } else {
            startDestination = HomeGraph
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination // TODO: Cambiar por startDestination
    ) {
        loginNavigationGraph(navHostController, appPreferences)

        configurationNavigationGraph(navHostController, appPreferences)

        homeNavigationGraph(navHostController, appPreferences)

        menuNavigationGraph(navHostController, appPreferences)
    }
}

@Serializable
object Menu

fun NavGraphBuilder.menuNavigationGraph(
    navHostController: NavHostController,
    appPreferences: AppPreferences
) {
    navigation<MenuGraph>(startDestination = Menu) {
        composable<Menu> {
            MenuScreen()
        }
    }
}