package com.soleel.finanzas.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

fun NavGraphBuilder.homeNavigationGraph(
    navHostController: NavHostController,
    appPreferences: AppPreferences
) {
    composable<HomeGraph> {
        HomeScreen(navHostController)
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

@Composable
fun HomeScreen(parentNavHostController: NavHostController) {
    val topBarScreens: List<HomeTopBarScreens<*>> = remember(
        calculation = {
            listOf(
                HomeTopBarScreens.Calculator,
                HomeTopBarScreens.Transactions,
                HomeTopBarScreens.Accounts
            )
        }
    )

    val navHostController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navHostController.currentBackStackEntryAsState()
                    .value?.destination

                topBarScreens.forEach { screen ->
                    val isSelected: Boolean = currentDestination?.route == screen.route

                    NavigationBarItem(
                        icon = {
                            if (isSelected) Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.name
                            )
                            else Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.name
                            )
                        },
                        label = { Text(screen.name) },
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navHostController.navigate(
                                    route = screen,
                                    navOptions = navOptions(
                                        optionsBuilder = {
                                            popUpTo(
                                                id = navHostController.graph
                                                    .findStartDestination().id,
                                                popUpToBuilder = {
                                                    saveState = true
                                                }
                                            )
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    )
                                )
                            }
                        }
                    )
                }
            }
        },
        content = { paddingValues ->
            NavHost(
                navController = navHostController,
                startDestination = HomeTopBarScreens.Calculator,
                modifier = Modifier.padding(paddingValues)
            ) {
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
        }
    )
}