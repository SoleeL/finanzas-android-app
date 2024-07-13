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
import com.soleel.finanzas.feature.transactions.screen.AllTransactionsListRoute
import com.soleel.finanzas.feature.transactions.screen.SummaryPeriodTransactionsListRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val TRANSACTIONS_GRAPH = "transactions_graph"
const val ALL_TRANSACTIONS_LIST_ROUTE = "$TRANSACTIONS_GRAPH/all_transactions_list_route"

const val SUMMARY_PERIOD_ARG = "summaryPeriod"

fun NavController.navigationToAllTransactionsListRoute(navOptions: NavOptions? = null) {
    this.navigate(TRANSACTIONS_GRAPH, navOptions)
}

internal class TransactionsArgs(val summaryPeriod: TransactionsLevelDestination) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                summaryPeriod = TransactionsLevelDestination.fromTitle(
                    URLDecoder.decode(
                        checkNotNull(savedStateHandle[SUMMARY_PERIOD_ARG]),
                        URL_CHARACTER_ENCODING
                    )
                )
            )
}

fun NavController.navigationToSummaryPeriodTransactionsListRoute(
    summaryPeriod: TransactionsLevelDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(createSummaryPeriodRoute(summaryPeriod))
}

fun createSummaryPeriodRoute(summaryPeriod: TransactionsLevelDestination): String {
    val encodedId = URLEncoder.encode(summaryPeriod.title, URL_CHARACTER_ENCODING)
    return "$TRANSACTIONS_GRAPH/$encodedId"
}

fun NavGraphBuilder.transactionGraph(
    finishApp: (Context) -> Unit
) {
    navigation(
        startDestination = ALL_TRANSACTIONS_LIST_ROUTE,
        route = TRANSACTIONS_GRAPH,
        builder = {
            allTransactionsListRoute(finishApp = finishApp)
            summaryPeriodTransactionsListRoute(finishApp = finishApp)
        }
    )
}

fun NavGraphBuilder.allTransactionsListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = ALL_TRANSACTIONS_LIST_ROUTE,
        content = {
            AllTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.summaryPeriodTransactionsListRoute(
    finishApp: (Context) -> Unit
) {
    composable(
        route = "$TRANSACTIONS_GRAPH/$SUMMARY_PERIOD_ARG",
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = { type = NavType.StringType }
            )
        ),
        content = {
            SummaryPeriodTransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}