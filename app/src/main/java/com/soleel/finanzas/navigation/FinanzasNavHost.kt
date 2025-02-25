package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
//import com.soleel.finanzas.feature.accounts.navigation.accountsScreen
//import com.soleel.finanzas.feature.createaccount.navigation.createAccountScreen
//import com.soleel.finanzas.feature.createtransaction.navigation.createTransactionScreen
//import com.soleel.finanzas.feature.profile.navigation.profileScreen
//import com.soleel.finanzas.feature.stats.navigation.statsScreen
//import com.soleel.finanzas.feature.transactions.navigation.TRANSACTIONS_ROUTE
//import com.soleel.finanzas.feature.transactions.navigation.transactionsGraph
//import com.soleel.finanzas.ui.FinanzasAppState

//
//@Composable
//fun FinanzasNavHost(
//    modifier: Modifier = Modifier,
//    appState: FinanzasAppState,
//    startDestination: String = TRANSACTIONS_ROUTE,
//) {
//    val navController = appState.navController
//
//    NavHost(
//        navController = navController,
//        startDestination = startDestination,
//        modifier = modifier,
//        builder = {
//            transactionsGraph(
//                finishApp = appState::finishApp
//            )
//
//            statsScreen(
//                finishApp = appState::finishApp
//            )
//
//            accountsScreen(
//                finishApp = appState::finishApp
//            )
//
//            profileScreen(
//                finishApp = appState::finishApp
//            )
//
//            createAccountScreen(
//                onBackToPreviousView = {
//                    navController.popBackStack()
//                    appState.hideExtendAddMenu()
//                    appState.showFloatingAddMenu()
//                    appState.showBottomBar()
//                    appState.updateTransactionsTab()
//                }
//            )
//
//            createTransactionScreen(
//                onBackToPreviousView = {
//                    navController.popBackStack()
//                    appState.hideExtendAddMenu()
//                    appState.showFloatingAddMenu()
//                    appState.showBottomBar()
//                    appState.updateTransactionsTab()
//                }
//            )
//        }
//    )
//}

//fun NavGraphBuilder.finanzas(navController: NavHostController): NavGraphBuilder {
//    homeScreen()
//    ...
//}