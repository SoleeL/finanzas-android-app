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
import com.soleel.finanzas.feature.home.MenuScreen
import com.soleel.finanzas.feature.home.homeNavigationGraph
import com.soleel.finanzas.feature.login.LoginGraph
import com.soleel.finanzas.feature.login.Signup
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
        startDestination = startDestination // TODO: Cambiar por startDestination
    ) {
        loginNavigationGraph(
            navigateToHomeGraph = {
                navHostController.navigate(
                    route = HomeGraph,
                    builder = {
                        popUpTo(LoginGraph) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                )
            },
            navigateToSignupScreen = {
                navHostController.navigate(Signup)
            },
            backToLoginScreen = {
                navHostController.popBackStack() // Regresa a Login
            },
            navigateToConfigureGraph = {
                navHostController.navigate(
                    route = ConfigurationGraph,
                    builder = {
                        popUpTo(LoginGraph) {
                            inclusive = true
                        }
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
                        popUpTo(ConfigurationGraph) {
                            inclusive = true
                        }
                    }
                )
            },
            appPreferences = appPreferences
        )

        homeNavigationGraph(navHostController, appPreferences)

        menuNavigationGraph(navHostController, appPreferences)
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeNavigationTopAppBar(
//    navHostController: NavHostController,
//    appPreferences: AppPreferences
//) {
//    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
//    val currentDestination: NavDestination? = navBackStackEntry?.destination
//
//    val topBarScreens: List<HomeTopBarScreens<*>> = remember(
//        calculation = {
//            listOf(
//                HomeTopBarScreens.Calculator,
//                HomeTopBarScreens.Transactions,
//                HomeTopBarScreens.Accounts
//            )
//        }
//    )
//
//    TopAppBar(
//        title = {
//            Text("finanzas")
//        },
//        modifier = Modifier.background(Color.DarkGray),
//        navigationIcon = {
//            IconButton(
//                onClick = { navHostController.navigate(route = Menu) },
//                content = {
//                    Icon(Icons.Default.Menu, contentDescription = "Menu")
//                }
//            )
//        },
//        actions = {
//            topBarScreens.forEach(action = { screen ->
//                val isSelected: Boolean = currentDestination?.route == screen.route
//                IconButton(
//                    onClick = {
//                        // LA PRIMERA NAVEGACION AL GRAPH NO SE CONSIDERA EL COMO NAVEGA AL PRIMERA SCREEN
//
//                        // IMPORTANT:
//                        //  1. Navegacion con estado persistente
//                        //  2. Un unico screen en el stack
//                        //  3. Todos los screen son el top, por lo que retroceder cierra la app
//                        //      3.a. Implementacion en cada screen
//                        if (!isSelected) {
//                            navHostController.navigate(
//                                route = screen,
//                                navOptions = navOptions(
//                                    optionsBuilder = {
//                                        popUpTo(
//                                            id = navHostController.graph
//                                                .findStartDestination().id,
//                                            popUpToBuilder = {
//                                                saveState = true
//                                            }
//                                        )
//                                        launchSingleTop = true
//                                        restoreState = true
//                                    }
//                                )
//                            )
//                        }
//                    },
//                    content = {
//                        Icon(
//                            painterResource(id = screen.icon),
//                            contentDescription = screen.name,
//                            tint = if (isSelected) Color.Blue else Color.Gray
//                        )
//                    }
//                )
//            })
//        }
//    )
//}

//@Composable
//fun AppBottomNavigation(navHostController: NavHostController) {
//    val topBarScreens: List<HomeTopBarScreens<*>> = remember(
//        calculation = {
//            listOf(
//                HomeTopBarScreens.Calculator,
//                HomeTopBarScreens.Transactions,
//                HomeTopBarScreens.Accounts
//            )
//        }
//    )
//
//    NavigationBar {
//        val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination
//
//        topBarScreens.forEach { screen ->
//            val isSelected: Boolean = currentDestination?.route == screen.route
//
//            NavigationBarItem(
//                icon = {
//                    if (isSelected) Icon(
//                        modifier = Modifier.size(24.dp),
//                        painter = painterResource(id = screen.icon),
//                        contentDescription = screen.name
//                    )
//                    else Icon(
//                        modifier = Modifier.size(24.dp),
//                        painter = painterResource(id = screen.icon),
//                        contentDescription = screen.name
//                    )
//                },
//                label = { Text(screen.name) },
//                selected = isSelected,
//                onClick = {
//                    if (!isSelected) {
//                        navHostController.navigate(
//                            route = screen,
//                            navOptions = navOptions(
//                                optionsBuilder = {
//                                    popUpTo(
//                                        id = navHostController.graph
//                                            .findStartDestination().id,
//                                        popUpToBuilder = {
//                                            saveState = true
//                                        }
//                                    )
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            )
//                        )
//                    }
//                }
//            )
//        }
//    }
//}

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