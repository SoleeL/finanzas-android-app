package com.soleel.finanzas.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.data.preferences.AppPreferences
import com.soleel.finanzas.feature.home.calculator.CalculatorScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

fun NavGraphBuilder.homeNavigationGraph(
    appPreferences: AppPreferences
) {
    composable<HomeGraph> {
        HomeScreen()
    }
}

@Serializable
sealed class HomeTopBarScreens<T>(val name: String, val icon: Int, val route: T) {
    @Serializable
    data object Calculator : HomeTopBarScreens<Calculator>(
        name = "Calculator",
        icon = R.drawable.ic_add_circle,
        route = Calculator
    )

    @Serializable
    data object Transactions : HomeTopBarScreens<Transactions>(
        name = "Transactions",
        icon = R.drawable.ic_add_transaction,
        route = Transactions
    )

    @Serializable
    data object Accounts : HomeTopBarScreens<Accounts>(
        name = "Accounts",
        icon = R.drawable.ic_accounts,
        route = Accounts
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navHostController: NavHostController = rememberNavController()
    val currentDestination: NavDestination? = navHostController.currentBackStackEntryAsState()
        .value?.destination

    val topBarScreens: List<HomeTopBarScreens<*>> = remember(
        calculation = {
            listOf(
                HomeTopBarScreens.Calculator,
                HomeTopBarScreens.Transactions,
                HomeTopBarScreens.Accounts
            )
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("finanzas")
                },
                modifier = Modifier.background(Color.DarkGray),
//                navigationIcon = {
//                    IconButton(
//                        onClick = { navHostController.navigate(route = Menu) },
//                        content = {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    )
//                },
                actions = {
                    topBarScreens.forEach(action = { screen ->
                        val isSelected = currentDestination?.hasRoute(screen::class) == true
                        IconButton(
                            onClick = {
                                // LA PRIMERA NAVEGACION AL GRAPH NO SE CONSIDERA EL COMO NAVEGA AL PRIMERA SCREEN

                                // IMPORTANT:
                                //  1. Navegacion con estado persistente
                                //  2. Un unico screen en el stack
                                //  3. Todos los screen son el top, por lo que retroceder cierra la app
                                //      3.a. Implementacion en cada screen
                                if (!isSelected) {
                                    navHostController.navigate(
                                        route = screen,
                                        navOptions = navOptions(
                                            optionsBuilder = {
                                                popUpTo(
                                                    id = navHostController.graph
                                                        .findStartDestination().id,
                                                    popUpToBuilder = {
                                                        //inclusive = true // SI QUIERO QUE SOLO EXISTA 1 SCREEN A LA VEZ Y AL RETROCEDER SE CIERRE LA APP
                                                        saveState = true
                                                    }
                                                )
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        )
                                    )
                                }
                            },
                            content = {
                                Icon(
                                    painterResource(id = screen.icon),
                                    contentDescription = screen.name,
                                    tint = if (isSelected) Color.Blue else Color.Gray
                                )
                            }
                        )
                    })
                }
            )
        },
        content = { paddingValues ->
            NavHost(
                navController = navHostController,
                startDestination = HomeTopBarScreens.Calculator,
                modifier = Modifier.padding(paddingValues),
                builder = {
                    composable<HomeTopBarScreens.Calculator> {
                        CalculatorScreen()
                    }

                    composable<HomeTopBarScreens.Transactions> {
                        TransactionsScreen()
                    }

                    composable<HomeTopBarScreens.Accounts> {
                        AccountsScreen()
                    }
                }
            )
        }
    )
}