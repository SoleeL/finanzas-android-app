package com.soleel.finanzas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.core.ui.utils.SmartphonePreview
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
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

@SmartphonePreview
@Composable
fun FinanzasNavigationGraphPreview() {
   FinanzasNavigationGraph(MockAppPreferences())
}

@Serializable
object AddGraph // Flujo invocado por la calculadora para ingresar una transaccion

@Composable
fun FinanzasNavigationGraph(
    startDestination: Any
) {
    val navHostController: NavHostController = rememberNavController()

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
                backToLoginScreen = { navHostController.popBackStack() }
            ) {
                navHostController.navigate(
                    route = ConfigurationGraph,
                    builder = {
                        popUpTo(
                            route = LoginGraph,
                            popUpToBuilder = { inclusive = true })
                    }
                )
            }

            configurationNavigationGraph(
                backToPrevious = { navHostController.popBackStack() },
                navigateToCurrencyScreen = { navHostController.navigate(Currency) },
                navigateToPaymentsScreen = { navHostController.navigate(Payments) },
                navigateToCalendarScreen = { navHostController.navigate(Calendar) },
                navigateToThemeScreen = { navHostController.navigate(Theme) },
                navigateToNotificationsScreen = { navHostController.navigate(Notifications) },
                navigateToPasswordScreen = { navHostController.navigate(Password) },
                navigateToBackupScreen = { navHostController.navigate(Backup) }
            ) {
                navHostController.navigate(
                    route = HomeGraph,
                    builder = {
                        popUpTo(
                            route = ConfigurationGraph,
                            popUpToBuilder = { inclusive = true })
                    }
                )
            }

            homeNavigationGraph()

            menuNavigationGraph()
        }
    )
}