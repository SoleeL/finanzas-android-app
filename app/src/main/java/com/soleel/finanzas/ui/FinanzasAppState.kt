package com.soleel.finanzas.ui

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.soleel.finanzas.feature.paymentaccountcreate.navigation.navigateToPaymentAccountCreateGraph
import com.soleel.finanzas.feature.paymentaccounts.navigation.navigateToAccounts
import com.soleel.finanzas.feature.profile.navigation.navigateToProfile
import com.soleel.finanzas.feature.stats.navigation.navigateToStats
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionCreateGraph
import com.soleel.finanzas.feature.transactions.navigation.allTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.navigationToAllTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.navigationToAnnuallyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.navigationToDailyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.navigationToMonthlyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.navigationToWeeklyTransactionsListRoute
import com.soleel.finanzas.feature.transactions.navigation.transactionsGraph
import com.soleel.finanzas.navigation.destination.TopLevelDestination
import com.soleel.finanzas.navigation.destination.TopLevelDestination.PAYMENT_ACCOUNTS
import com.soleel.finanzas.navigation.destination.TopLevelDestination.PROFILE
import com.soleel.finanzas.navigation.destination.TopLevelDestination.STATS
import com.soleel.finanzas.navigation.destination.TopLevelDestination.TRANSACTIONS
import com.soleel.finanzas.navigation.destination.TransactionsLevelDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberFinanzasAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    showTransactionsTab: MutableState<Boolean> = remember { mutableStateOf(true) },
    showBottomBar: MutableState<Boolean> = remember { mutableStateOf(true) },
    showFloatingAddMenu: MutableState<Boolean> = remember { mutableStateOf(true) },
    showExtendAddMenu: MutableState<Boolean> = remember { mutableStateOf(false) },
    showCancelAlert: MutableState<Boolean> = remember { mutableStateOf(false) }
): FinanzasAppState {

    return remember(
        key1 = navController,
        key2 = coroutineScope,
//        calculation = ::createAppState
        calculation = {
            FinanzasAppState(
                navController = navController,
                coroutineScope = coroutineScope,
                showTransactionsTab = showTransactionsTab,
                showBottomBar = showBottomBar,
                showFloatingAddMenu = showFloatingAddMenu,
                showExtendAddMenu = showExtendAddMenu,
                showCancelAlert = showCancelAlert
            )
        }
    )
}

private fun createAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    showTransactionsTab: MutableState<Boolean>,
    showBottomBar: MutableState<Boolean>,
    showFloatingAddMenu: MutableState<Boolean>,
    showExtendAddMenu: MutableState<Boolean>,
    showCancelAlert: MutableState<Boolean>
): FinanzasAppState {
    return FinanzasAppState(
        navController = navController,
        coroutineScope = coroutineScope,
        showTransactionsTab = showTransactionsTab,
        showBottomBar = showBottomBar,
        showFloatingAddMenu = showFloatingAddMenu,
        showExtendAddMenu = showExtendAddMenu,
        showCancelAlert = showCancelAlert
    )
}

class FinanzasAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val showTransactionsTab: MutableState<Boolean>,
    val showBottomBar: MutableState<Boolean>,
    val showFloatingAddMenu: MutableState<Boolean>,
    val showExtendAddMenu: MutableState<Boolean>,
    val showCancelAlert: MutableState<Boolean>,
) {

    @Composable
    fun getCurrentDestination(): NavDestination? {
        return navController.currentBackStackEntryAsState().value?.destination
    }

    @Composable
    fun getCurrentTopLevelDestination(): TopLevelDestination? {
        return when (getCurrentDestination()?.route) {
            transactionsGraph -> TRANSACTIONS
            else -> null
        }
    }

    fun topLevelDestinations(): List<TopLevelDestination> {
        return TopLevelDestination.entries
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace(
            label = "Navigation: ${topLevelDestination.name}",
            block = {
                val topLevelNavOptions = navOptions(
                    optionsBuilder = {
                        popUpTo(
                            id = navController.graph.findStartDestination().id,
                            popUpToBuilder = { saveState = true }
                        )
                        launchSingleTop = true
                        restoreState = true
                    }
                )

                when (topLevelDestination) {
                    TRANSACTIONS -> navController.navigationToAllTransactionsListRoute(topLevelNavOptions)

                    PAYMENT_ACCOUNTS -> navController.navigateToAccounts(topLevelNavOptions)

                    STATS -> navController.navigateToStats(topLevelNavOptions)

                    PROFILE -> navController.navigateToProfile(topLevelNavOptions)
                }
            }
        )
    }

    fun transactionsLevelDestinations(): List<TransactionsLevelDestination> {
        return TransactionsLevelDestination.entries
    }

    fun navigateToTransactionsLevelDestination(transactionsLevelDestination: TransactionsLevelDestination) {
        trace(
            label = "Navigation: ${transactionsLevelDestination.name}",
            block = {
                val transactionsLevelNavOptions = navOptions(
                    optionsBuilder = {
                        popUpTo(
                            id = navController.graph.findStartDestination().id,
                            popUpToBuilder = { saveState = true }
                        )
                        launchSingleTop = true
                        restoreState = true
                    }
                )

                when (transactionsLevelDestination) {
                    TransactionsLevelDestination.ALL_TRANSACTIONS -> navController.navigationToAllTransactionsListRoute(
                        transactionsLevelNavOptions
                    )

                    TransactionsLevelDestination.DAILY_TRANSACTIONS -> navController.navigationToDailyTransactionsListRoute(
                        transactionsLevelNavOptions
                    )

                    TransactionsLevelDestination.WEEKLY_TRANSACTIONS -> navController.navigationToWeeklyTransactionsListRoute(
                        transactionsLevelNavOptions
                    )

                    TransactionsLevelDestination.MONTHLY_TRANSACTIONS -> navController.navigationToMonthlyTransactionsListRoute(
                        transactionsLevelNavOptions
                    )

                    TransactionsLevelDestination.ANNUALLY_TRANSACTIONS -> navController.navigationToAnnuallyTransactionsListRoute(
                        transactionsLevelNavOptions
                    )
                }
            }
        )
    }

    fun finishApp(context: Context) {
        (context as Activity).finish()
    }

    fun navigateToPaymentAccountCreate() {
        navController.navigateToPaymentAccountCreateGraph()
    }

    fun navigateToTransactionCreate() {
        navController.navigateToTransactionCreateGraph()
    }

    fun backToHome() {
        navController.popBackStack(route = allTransactionsListRoute, inclusive = false)
    }

    fun shouldShowTransactionsTab(): Boolean {
        return this.showTransactionsTab.value
    }

    fun showTransactionsTab() {
        this.showTransactionsTab.value = true
    }

    fun hideTransactionsTab() {
        this.showTransactionsTab.value = false
    }

    fun shouldShowBottomBar(): Boolean {
        return this.showBottomBar.value
    }

    fun showBottomBar() {
        this.showBottomBar.value = true
    }

    fun hideBottomBar() {
        this.showBottomBar.value = false
    }

    fun shouldShowFloatingAddMenu(): Boolean {
        return this.showFloatingAddMenu.value
    }

    fun showFloatingAddMenu() {
        this.showFloatingAddMenu.value = true
    }

    fun hideFloatingAddMenu() {
        this.showFloatingAddMenu.value = false
    }

    fun shouldShowExtendAddMenu(): Boolean {
        return this.showExtendAddMenu.value
    }

    fun showExtendAddMenu() {
        this.showExtendAddMenu.value = true
    }

    fun hideExtendAddMenu() {
        this.showExtendAddMenu.value = false
    }

    fun shouldShowCancelAlert(): Boolean {
        return this.showCancelAlert.value
    }

    fun showCancelAlert() {
        this.showCancelAlert.value = true
    }

    fun hideCancelAlert() {
        this.showCancelAlert.value = false
    }
}

