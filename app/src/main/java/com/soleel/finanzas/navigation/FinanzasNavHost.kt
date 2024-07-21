package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.soleel.finanzas.feature.accounts.navigation.accountsScreen
import com.soleel.finanzas.feature.createaccount.navigation.createAccountScreen
import com.soleel.finanzas.feature.profile.navigation.profileScreen
import com.soleel.finanzas.feature.stats.navigation.statsScreen
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionAccountRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionAmountRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionCategoryRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionNameRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionTypeRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.transactionCreateGraph
import com.soleel.finanzas.feature.transactions.navigation.TRANSACTIONS_ROUTE
import com.soleel.finanzas.feature.transactions.navigation.transactionsGraph
import com.soleel.finanzas.ui.FinanzasAppState


@Composable
fun FinanzasNavHost(
    appState: FinanzasAppState,
    modifier: Modifier = Modifier,
    startDestination: String = TRANSACTIONS_ROUTE,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = {
            transactionsGraph(
                finishApp = appState::finishApp
            )

            statsScreen(
                finishApp = appState::finishApp
            )

            accountsScreen(
                finishApp = appState::finishApp
            )

            profileScreen(
                finishApp = appState::finishApp
            )

            createAccountScreen(
                onBackToPreviousView = {
                    navController.popBackStack()
                    appState.hideExtendAddMenu()
                    appState.showFloatingAddMenu()
                    appState.showBottomBar()
                    appState.updateTransactionsTab()
                }
            )

            transactionCreateGraph(
                navController = navController,
                onAcceptCancel = {
                    appState.popBackStackTransactionCreate()
                    appState.hideExtendAddMenu()
                    appState.showFloatingAddMenu()
                    appState.showBottomBar()
                    appState.updateTransactionsTab()
                },
                onBackClick = navController::popBackStack,
                fromInitToAccount = navController::navigateToTransactionAccountRoute,
                fromAccountToType = navController::navigateToTransactionTypeRoute,
                fromTypeToCategory = navController::navigateToTransactionCategoryRoute,
                fromCategoryToName = navController::navigateToTransactionNameRoute,
                fromNameToAmount = navController::navigateToTransactionAmountRoute,
                onTransactionSaved = {
                    appState.backToTransactions()
                    appState.hideExtendAddMenu()
                    appState.showFloatingAddMenu()
                    appState.showBottomBar()
                    appState.updateTransactionsTab()
                }
            )
        }
    )
}

//fun NavGraphBuilder.finanzas(navController: NavHostController): NavGraphBuilder {
//    homeScreen()
//    ...
//}