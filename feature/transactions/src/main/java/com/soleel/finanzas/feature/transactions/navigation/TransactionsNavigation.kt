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
import com.soleel.finanzas.feature.transactions.TransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination.Companion.lowercase
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val TRANSACTIONS_ROUTE = "transactions_route" // Las rutas con _

const val SUMMARY_PERIOD_ARG = "summaryPeriod" // Los argumentos son con camellcase

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

fun NavController.navigateToTransactions(
    transactionsLevelDestination: TransactionsLevelDestination = TransactionsLevelDestination.ALL,
    navOptions: NavOptions? = null
) {
    this.navigate(createRoute(subRoute = transactionsLevelDestination.lowercase()), navOptions)
}

fun createRoute(subRoute: String): String {
    val encodedSubRoute = URLEncoder.encode(subRoute, URL_CHARACTER_ENCODING)
    return "$TRANSACTIONS_ROUTE/$encodedSubRoute"
}

fun NavGraphBuilder.transactionGraph(
    finishApp: (Context) -> Unit
) {
    navigation(
        startDestination = "$TRANSACTIONS_ROUTE/all",
        route = TRANSACTIONS_ROUTE,
        builder = {
            allTransactionsScreen(finishApp = finishApp)
            transactionsScreen(finishApp = finishApp)
        }
    )
}

fun NavGraphBuilder.allTransactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = "$TRANSACTIONS_ROUTE/all",
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = { type = NavType.StringType; defaultValue = "all" }
            )
        ),
        content = {
            TransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}

fun NavGraphBuilder.transactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = "$TRANSACTIONS_ROUTE/{$SUMMARY_PERIOD_ARG}",
        arguments = listOf(
            navArgument(
                name = SUMMARY_PERIOD_ARG,
                builder = { type = NavType.StringType }
            )
        ),
        content = {
            TransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}