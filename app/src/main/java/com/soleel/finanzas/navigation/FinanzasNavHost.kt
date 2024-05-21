package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.soleel.finanzas.feature.paymentaccounts.navigation.accountsScreen
import com.soleel.finanzas.ui.FinanzasAppState
import com.soleel.finanzas.feature.paymentaccountcreate.navigation.navigateToPaymentAccountAmountRoute
import com.soleel.finanzas.feature.paymentaccountcreate.navigation.navigateToPaymentAccountNameRoute
import com.soleel.finanzas.feature.paymentaccountcreate.navigation.paymentAccountCreateGraph
import com.soleel.finanzas.feature.profile.navigation.profileScreen
import com.soleel.finanzas.feature.stats.navigation.statsScreen
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionAmountRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionCategoryRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionNameRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionPaymentAccountRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionTypeRoute
import com.soleel.finanzas.feature.transactioncreate.navigation.transactionCreateGraph
import com.soleel.finanzas.feature.transactions.navigation.transactionGraph
import com.soleel.finanzas.feature.transactions.navigation.transactionsGraph


@Composable
fun FinanzasNavHost(
    appState: FinanzasAppState,
    modifier: Modifier = Modifier,
    startDestination: String = transactionsGraph,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
//        builder = NavGraphBuilder.finanzas(navController)
        builder = {
            transactionGraph(
                navController = navController
            )

            statsScreen()

            accountsScreen()

            profileScreen()

            paymentAccountCreateGraph(
                navController = navController,
                showBottomBar = appState::showBottomBar,
                showFloatingAddMenu = appState::showFloatingAddMenu,
                hideExtendAddMenu = appState::hideExtendAddMenu,
                onBackClick = navController::popBackStack,
                onCancelClick = appState::showCancelAlert,
                onSaveClick = appState::backToHome,
                fromTypeToName = navController::navigateToPaymentAccountNameRoute,
                fromNameToAmount = navController::navigateToPaymentAccountAmountRoute
            )

            transactionCreateGraph(
                navController = navController,
                showBottomBar = appState::showBottomBar,
                showFloatingAddMenu = appState::showFloatingAddMenu,
                hideExtendAddMenu = appState::hideExtendAddMenu,
                onBackClick = navController::popBackStack,
                onCancelClick = appState::showCancelAlert,
                onSaveClick = appState::backToHome,
                fromInitToPaymentAccount = navController::navigateToTransactionPaymentAccountRoute,
                fromPaymentAccountToType = navController::navigateToTransactionTypeRoute,
                fromTypeToCategory = navController::navigateToTransactionCategoryRoute,
                fromCategoryToName = navController::navigateToTransactionNameRoute,
                fromNameToAmount = navController::navigateToTransactionAmountRoute,
            )
        }
    )
}

//fun NavGraphBuilder.finanzas(navController: NavHostController): NavGraphBuilder {
//    homeScreen()
//
//    statsScreen()
//
//    accountsScreen()
//
//    profileScreen()
//
//    createPaymentAccountGraph(
//        onShowBottomBar = appState::showBottomBar,
//        onShowAddFloating = appState::showAddFloating,
//        onBackClick = navController::popBackStack,
//        onCancelClick = appState::showCancelAlert,
//        nestedGraphs = {
//            selectTypePaymentAccountScreen()
//            enterTransactionNameScreen()
//            enterTransactionAmountScreen()
//        }
//    )
//
//    transactionCreateScreen(
//        onShowBottomBar = appState::showBottomBar,
//        onShowAddFloating = appState::showAddFloating,
//        onBackClick = navController::popBackStack,
//        onCancelClick = appState::showCancelAlert,
//    )
//}