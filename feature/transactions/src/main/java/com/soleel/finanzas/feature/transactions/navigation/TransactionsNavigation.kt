package com.soleel.finanzas.feature.transactions.navigation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

fun NavController.navigationToTransactionsRoute(
    transactionsLevelDestination: TransactionsLevelDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(createRoute(transactionsLevelDestination.lowercase()), navOptions)
}

fun createRoute(subRoute: String): String {
    val encodedSubRoute = URLEncoder.encode(subRoute, URL_CHARACTER_ENCODING)
    return "$TRANSACTIONS_ROUTE/$encodedSubRoute"
}

fun NavGraphBuilder.transactionsScreen(
    finishApp: (Context) -> Unit
) {
    composable(
        route = "$TRANSACTIONS_ROUTE/{$SUMMARY_PERIOD_ARG}",
        arguments = listOf(
            navArgument(SUMMARY_PERIOD_ARG) { type = NavType.StringType; defaultValue = "all" }
        ),
        content = {
            TransactionsListRoute(
                finishApp = finishApp
            )
        }
    )
}