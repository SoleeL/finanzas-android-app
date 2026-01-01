package com.soleel.finanzas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.feature.configuration.Backup
import com.soleel.finanzas.feature.configuration.Calendar
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.configuration.Currency
import com.soleel.finanzas.feature.configuration.Notifications
import com.soleel.finanzas.feature.configuration.Password
import com.soleel.finanzas.feature.configuration.Payments
import com.soleel.finanzas.feature.configuration.Theme
import com.soleel.finanzas.feature.configuration.configurationNavigationGraph
import com.soleel.finanzas.feature.createexpense.CreateExpenseGraph
import com.soleel.finanzas.feature.createexpense.createExpenseNavigationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.home.homeNavigationGraph
import com.soleel.finanzas.feature.launch.LaunchGraph
import com.soleel.finanzas.feature.launch.launchNavigationGraph
import com.soleel.finanzas.feature.login.LoginGraph
import com.soleel.finanzas.feature.login.Signup
import com.soleel.finanzas.feature.login.loginNavigationGraph
import com.soleel.finanzas.navigation.createListNavType
import kotlin.reflect.typeOf


@Composable
fun MainNavigationGraph() {
    val navHostController: NavHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = LaunchGraph,
//        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None },
//        popEnterTransition = { EnterTransition.None },
//        popExitTransition = { ExitTransition.None },
        builder = {
            composable<LaunchGraph> {
                launchNavigationGraph(
                    navigateToConfigurationGraph = {
                        navHostController.navigate(
                            route = HomeGraph,
                            builder = {
                                popUpTo(route = LaunchGraph, popUpToBuilder = { inclusive = true })
                                launchSingleTop = true
                                restoreState = true
                            }
                        )
                    },
                    navigateToHomeGraph = {
                        navHostController.navigate(
                            route = HomeGraph,
                            builder = {
                                popUpTo(route = LaunchGraph, popUpToBuilder = { inclusive = true })
                                launchSingleTop = true
                                restoreState = true
                            }
                        )
                    }
                )
            }

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
                }
            )

            homeNavigationGraph(
                navigateToCreateExpenseGraph = { items: List<Item> ->
                    navHostController.navigate(CreateExpenseGraph(items = items))
                }
            )

            createExpenseNavigationGraph(
                backToPrevious = { navHostController.popBackStack() },
                itemsToNavType = mapOf(typeOf<List<Item>>() to createListNavType<Item>())
            )

//            createAccountNavigationGraph(
//                backToPrevious = { navHostController.popBackStack() },
//                itemsToNavType = mapOf(typeOf<List<Item>>() to createListNavType<Item>())
//            )
        }
    )
}