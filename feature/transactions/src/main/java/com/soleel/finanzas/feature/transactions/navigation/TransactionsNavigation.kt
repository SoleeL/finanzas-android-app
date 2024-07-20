package com.soleel.finanzas.feature.transactions.navigation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination.Companion.lowercase
import com.soleel.finanzas.feature.transactions.screen.AllTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.TransactionsSummaryListRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val TRANSACTIONS_ROUTE = "transactions"

const val ALL_TRANSACTIONS_ROUTE = "$TRANSACTIONS_ROUTE/all"
const val DAILY_TRANSACTIONS_ROUTE = "$TRANSACTIONS_ROUTE/daily"
const val WEEKLY_TRANSACTIONS_ROUTE = "$TRANSACTIONS_ROUTE/weekly"
const val MONTHLY_TRANSACTIONS_ROUTE = "$TRANSACTIONS_ROUTE/monthly"

const val SUMMARY_PERIOD_ARG = "summaryPeriod" // Los argumentos son con camellcase

internal class TransactionsArgs(val summaryPeriod: TransactionsLevelDestination) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                summaryPeriod = TransactionsLevelDestination.fromName(
                    URLDecoder.decode(
                        checkNotNull(savedStateHandle[SUMMARY_PERIOD_ARG]),
                        URL_CHARACTER_ENCODING
                    )
                )
            )
}

fun NavController.navigateToAllTransactions(
    navOptions: NavOptions? = null
) {
    this.navigate(ALL_TRANSACTIONS_ROUTE, navOptions)
}

fun NavController.navigateToSummaryPeriodTransactions(
    transactionsLevelDestination: TransactionsLevelDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(createRoute(subRoute = transactionsLevelDestination.lowercase()), navOptions)
}

private fun createRoute(subRoute: String): String {
    val encodedSubRoute = URLEncoder.encode(subRoute, URL_CHARACTER_ENCODING)
    return "$TRANSACTIONS_ROUTE/$encodedSubRoute"
}

fun NavGraphBuilder.transactionsGraph(
    finishApp: (Context) -> Unit
) {
    navigation(
        startDestination = "$TRANSACTIONS_ROUTE/all",
        route = TRANSACTIONS_ROUTE,
        builder = {
            allTransactionsScreen(finishApp = finishApp)
            dailyTransactionsScreen(finishApp = finishApp)
            weeklyTransactionsScreen(finishApp = finishApp)
            monthlyTransactionsScreen(finishApp = finishApp)
        }
    )
}

private fun NavGraphBuilder.allTransactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = ALL_TRANSACTIONS_ROUTE,
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = {
                    type = NavType.StringType; defaultValue =
                    TransactionsLevelDestination.ALL.lowercase()
                }
            )
        ),
        content = {
            AllTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

private fun NavGraphBuilder.dailyTransactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = DAILY_TRANSACTIONS_ROUTE,
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = {
                    type = NavType.StringType; defaultValue =
                    TransactionsLevelDestination.DAILY.lowercase()
                }
            )
        ),
        content = {
            TransactionsSummaryListRoute(
                finishApp = finishApp
            )
        }
    )
}

private fun NavGraphBuilder.weeklyTransactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = WEEKLY_TRANSACTIONS_ROUTE,
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = {
                    type = NavType.StringType; defaultValue =
                    TransactionsLevelDestination.WEEKLY.lowercase()
                }
            )
        ),
        content = {
            TransactionsSummaryListRoute(
                finishApp = finishApp
            )
        }
    )
}

private fun NavGraphBuilder.monthlyTransactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = MONTHLY_TRANSACTIONS_ROUTE,
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = {
                    type = NavType.StringType; defaultValue =
                    TransactionsLevelDestination.MONTHLY.lowercase()
                }
            )
        ),
        content = {
            TransactionsSummaryListRoute(
                finishApp = finishApp
            )
        }
    )
}