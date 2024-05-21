package com.soleel.finanzas.feature.transactions.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.soleel.finanzas.feature.transactions.TransactionsRoute
import com.soleel.finanzas.feature.transactions.TransactionsViewModel
import com.soleel.finanzas.feature.transactions.screen.AnnuallyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.DailyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.MonthlyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.TransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.WeeklyTransactionsListRoute


const val transactionsGraph = "transactions_graph"

const val transactionsRoute = "transaction_route"
const val transactionsListRoute = "transactions_list_route"
const val dailyTransactionsListRoute = "daily_transactions_list_route"
const val weeklyTransactionsListRoute = "weekly_transactions_list_route"
const val monthlyTransactionsListRoute = "monthly_transactions_list_route"
const val annuallyTransactionsListRoute = "annually_transactions_list_route"

fun NavController.navigationToTransactionGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionsGraph, navOptions)
}

fun NavController.navigationToTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionsListRoute, navOptions)
}

fun NavController.navigationToDailyTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(dailyTransactionsListRoute, navOptions)
}

fun NavController.navigationToWeeklyTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(weeklyTransactionsListRoute, navOptions)
}

fun NavController.navigationToMonthlyTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(monthlyTransactionsListRoute, navOptions)
}

fun NavController.navigationToAnnuallyTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(annuallyTransactionsListRoute, navOptions)
}

fun NavGraphBuilder.transactionGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = transactionsRoute,
        route = transactionsGraph,
        builder = {
            transactionsRoute(
                navController = navController
            )
            transactionsListRoute(
                navController = navController
            )
            dailyTransactionsListRoute(
                navController = navController
            )
            weeklyTransactionListRoute(
                navController = navController
            )
            monthlyTransactionListRoute(
                navController = navController
            )
            annuallyTransactionListRoute(
                navController = navController
            )
        }
    )
}

fun NavGraphBuilder.transactionsRoute(
    navController: NavHostController,
) {
    composable(
        route = transactionsRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionsRoute(
                fromTransactionsToTransactionsList = navController::navigationToTransactionsListRoute,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionsListRoute(
    navController: NavHostController,
) {
    composable(
        route = transactionsListRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionsListRoute(viewModel = viewModel)
        }
    )
}

fun NavGraphBuilder.dailyTransactionsListRoute(
    navController: NavHostController,
) {
    composable(
        route = dailyTransactionsListRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            DailyTransactionsListRoute(viewModel = viewModel)
        }
    )
}

fun NavGraphBuilder.weeklyTransactionListRoute(
    navController: NavHostController,
) {
    composable(
        route = weeklyTransactionsListRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            WeeklyTransactionsListRoute(viewModel = viewModel)
        }
    )
}

fun NavGraphBuilder.monthlyTransactionListRoute(
    navController: NavHostController,
) {
    composable(
        route = monthlyTransactionsListRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            MonthlyTransactionsListRoute(viewModel = viewModel)
        }
    )
}

fun NavGraphBuilder.annuallyTransactionListRoute(
    navController: NavHostController,
) {
    composable(
        route = annuallyTransactionsListRoute,
        content = {
            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionsGraph)
                }
            )

            val viewModel: TransactionsViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            AnnuallyTransactionsListRoute(viewModel = viewModel)
        }
    )
}
