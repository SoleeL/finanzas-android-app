package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.soleel.accounts.navigation.accountsScreen
import com.soleel.finanzas.ui.FinanzasAppState
import com.soleel.home.navigation.homeRoute
import com.soleel.home.navigation.homeScreen
import com.soleel.paymentaccountcreate.navigation.navigateToPaymentAccountAmountRoute
import com.soleel.paymentaccountcreate.navigation.navigateToPaymentAccountNameRoute
import com.soleel.paymentaccountcreate.navigation.paymentAccountCreateGraph
import com.soleel.profile.navigation.profileScreen
import com.soleel.stats.navigation.statsScreen
import com.soleel.transactioncreate.navigation.navigateToTransactionAmountRoute
import com.soleel.transactioncreate.navigation.navigateToTransactionCategoryRoute
import com.soleel.transactioncreate.navigation.navigateToTransactionNameRoute
import com.soleel.transactioncreate.navigation.navigateToTransactionPaymentAccountRoute
import com.soleel.transactioncreate.navigation.navigateToTransactionTypeRoute
import com.soleel.transactioncreate.navigation.transactionCreateGraph


@Composable
fun FinanzasNavHost(
    appState: FinanzasAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
//        builder = NavGraphBuilder.finanzas(navController)
        builder = {
            homeScreen()

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