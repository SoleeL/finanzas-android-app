package com.soleel.finanzas.feature.transactions.navigation

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.feature.transactions.screen.AllTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.AnnuallyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.DailyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.MonthlyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.WeeklyTransactionsListRoute
import kotlin.reflect.KFunction1


const val transactionsGraph = "transactions_graph"

const val allTransactionsListRoute = "$transactionsGraph/all_transactions_list_route"
const val dailyTransactionsListRoute = "$transactionsGraph/daily_transactions_list_route"
const val weeklyTransactionsListRoute = "$transactionsGraph/weekly_transactions_list_route"
const val monthlyTransactionsListRoute = "$transactionsGraph/monthly_transactions_list_route"
const val annuallyTransactionsListRoute = "$transactionsGraph/annually_transactions_list_route"

//fun NavController.navigationToTransactionGraph(navOptions: NavOptions? = null) {
//    this.navigate(transactionsGraph, navOptions)
//}

fun NavController.navigationToAllTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(allTransactionsListRoute, navOptions)
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
    finishApp: (Context) -> Unit
) {
    navigation(
        startDestination = allTransactionsListRoute,
        route = transactionsGraph,
        builder = {
            allTransactionsListRoute(finishApp = finishApp)
            dailyTransactionsListRoute(finishApp = finishApp)
            weeklyTransactionListRoute(finishApp = finishApp)
            monthlyTransactionListRoute(finishApp = finishApp)
            annuallyTransactionListRoute(finishApp = finishApp)
        }
    )
}

//fun NavGraphBuilder.transactionsRoute() {
//    composable(
//        route = transactionsRoute,
//        content = {
//            TransactionsRoute()
//        }
//    )
//}

fun NavGraphBuilder.allTransactionsListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = allTransactionsListRoute,
        content = {
            AllTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.dailyTransactionsListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = dailyTransactionsListRoute,
        content = {
            DailyTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.weeklyTransactionListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = weeklyTransactionsListRoute,
        content = {
            WeeklyTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.monthlyTransactionListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = monthlyTransactionsListRoute,
        content = {
            MonthlyTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.annuallyTransactionListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = annuallyTransactionsListRoute,
        content = {
            AnnuallyTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}
