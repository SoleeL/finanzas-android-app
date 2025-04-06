package com.soleel.finanzas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.feature.configuration.Backup
import com.soleel.finanzas.feature.configuration.Calendar
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.configuration.Currency
import com.soleel.finanzas.feature.configuration.Notifications
import com.soleel.finanzas.feature.configuration.Password
import com.soleel.finanzas.feature.configuration.Payments
import com.soleel.finanzas.feature.configuration.Theme
import com.soleel.finanzas.feature.configuration.configurationNavigationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.home.homeNavigationGraph
import com.soleel.finanzas.feature.login.LoginGraph
import com.soleel.finanzas.feature.login.Signup
import com.soleel.finanzas.feature.login.loginNavigationGraph
import com.soleel.finanzas.feature.menu.menuNavigationGraph
import kotlinx.serialization.Serializable

@Serializable
object AddGraph // Flujo invocado por la calculadora para ingresar una transaccion

@Composable
fun FinanzasNavigationGraph(
    appPreferences: IAppPreferences
) {
    val navHostController: NavHostController = rememberNavController()

    val isLoggedIn: Boolean = appPreferences.getAuthToken()
        .collectAsState(initial = null).value != null
    val isAppConfigured: Boolean = appPreferences.getConfiguration()
        .collectAsState(initial = null).value != null

    val startDestination: Any = if (!isLoggedIn) {
        LoginGraph
    } else {
        if (!isAppConfigured) {
            ConfigurationGraph
        } else {
            HomeGraph
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        builder = {
            loginNavigationGraph(
                navigateToHomeGraph = {
                    navHostController.navigate(
                        route = HomeGraph,
                        builder = {
                            popUpTo(route = LoginGraph, popUpToBuilder = { inclusive = true })
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                },
                navigateToSignupScreen = { navHostController.navigate(Signup) },
                backToLoginScreen = { navHostController.popBackStack() },
                navigateToConfigureGraph = {
                    navHostController.navigate(
                        route = ConfigurationGraph,
                        builder = {
                            popUpTo(
                                route = LoginGraph,
                                popUpToBuilder = { inclusive = true })
                        }
                    )
                },
                appPreferences
            )

            configurationNavigationGraph(
                backToPrevious = { navHostController.popBackStack() },
                navigateToCurrencyScreen = { navHostController.navigate(Currency) },
                navigateToPaymentsScreen = { navHostController.navigate(Payments) },
                navigateToCalendarScreen = { navHostController.navigate(Calendar) },
                navigateToThemeScreen = { navHostController.navigate(Theme) },
                navigateToNotificationsScreen = { navHostController.navigate(Notifications) },
                navigateToPasswordScreen = { navHostController.navigate(Password) },
                navigateToBackupScreen = { navHostController.navigate(Backup) },
                navigateToHomeGraph = {
                    navHostController.navigate(
                        route = HomeGraph,
                        builder = {
                            popUpTo(
                                route = ConfigurationGraph,
                                popUpToBuilder = { inclusive = true })
                        }
                    )
                },
                appPreferences = appPreferences
            )

            homeNavigationGraph(appPreferences)

            menuNavigationGraph(appPreferences)
        }
    )
}