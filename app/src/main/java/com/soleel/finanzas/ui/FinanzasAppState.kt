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
import com.soleel.finanzas.feature.accounts.navigation.ACCOUNTS_ROUTE
import com.soleel.finanzas.feature.accounts.navigation.navigateToAccounts
import com.soleel.finanzas.feature.createaccount.navigation.CREATE_ACCOUNT_ROUTE
import com.soleel.finanzas.feature.createaccount.navigation.navigateToCreateAccountRoute
import com.soleel.finanzas.feature.profile.navigation.navigateToProfile
import com.soleel.finanzas.feature.stats.navigation.navigateToStats
import com.soleel.finanzas.feature.transactioncreate.navigation.navigateToTransactionCreateGraph
import com.soleel.finanzas.feature.transactioncreate.navigation.transactionCreateRoute
import com.soleel.finanzas.feature.transactions.navigation.ALL_TRANSACTIONS_ROUTE
import com.soleel.finanzas.feature.transactions.navigation.TRANSACTIONS_ROUTE
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import com.soleel.finanzas.feature.transactions.navigation.destination.isTransactionsLevelDestination
import com.soleel.finanzas.feature.transactions.navigation.navigateToAllTransactions
import com.soleel.finanzas.feature.transactions.navigation.navigateToSummaryPeriodTransactions
import com.soleel.finanzas.navigation.destination.TopLevelDestination
import com.soleel.finanzas.navigation.destination.TopLevelDestination.ACCOUNTS
import com.soleel.finanzas.navigation.destination.TopLevelDestination.PROFILE
import com.soleel.finanzas.navigation.destination.TopLevelDestination.STATS
import com.soleel.finanzas.navigation.destination.TopLevelDestination.TRANSACTIONS
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberFinanzasAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    showTransactionsTab: MutableState<Boolean> = remember { mutableStateOf(true) },
    showBottomBar: MutableState<Boolean> = remember { mutableStateOf(true) },
    showFloatingAddMenu: MutableState<Boolean> = remember { mutableStateOf(true) },
    showExtendAddMenu: MutableState<Boolean> = remember { mutableStateOf(false) },
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
): FinanzasAppState {
    return FinanzasAppState(
        navController = navController,
        coroutineScope = coroutineScope,
        showTransactionsTab = showTransactionsTab,
        showBottomBar = showBottomBar,
        showFloatingAddMenu = showFloatingAddMenu,
        showExtendAddMenu = showExtendAddMenu,
    )
}

class FinanzasAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val showTransactionsTab: MutableState<Boolean>,
    val showBottomBar: MutableState<Boolean>,
    val showFloatingAddMenu: MutableState<Boolean>,
    val showExtendAddMenu: MutableState<Boolean>,
) {

    @Composable
    fun getCurrentDestination(): NavDestination? {
        return this.navController.currentBackStackEntryAsState().value?.destination
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
                    TRANSACTIONS -> this.navController.navigateToAllTransactions(navOptions = topLevelNavOptions)

                    ACCOUNTS -> this.navController.navigateToAccounts(navOptions = topLevelNavOptions)

                    STATS -> this.navController.navigateToStats(navOptions = topLevelNavOptions)

                    PROFILE -> this.navController.navigateToProfile(navOptions = topLevelNavOptions)
                }
            }
        )
    }

    fun transactionsLevelDestinations(): List<TransactionsLevelDestination> {
        return TransactionsLevelDestination.entries
    }

    fun finishApp(context: Context) {
        (context as Activity).finish()
    }

    fun navigateToTransactions(transactionsLevelDestination: TransactionsLevelDestination) {
        this.navController.navigateToSummaryPeriodTransactions(transactionsLevelDestination = transactionsLevelDestination)
    }

    fun navigateToCreateAccount() {
        this.navController.navigateToCreateAccountRoute()
    }

    fun navigateToTransactionCreate() {
        this.navController.navigateToTransactionCreateGraph()
    }

    fun popBackStackTransactionCreate() {
        this.navController.popBackStack(route = transactionCreateRoute, inclusive = true)
    }

    fun backToTransactions() {
        this.navController.popBackStack(
            route = ALL_TRANSACTIONS_ROUTE,
            inclusive = false
        )
    }

    fun shouldShowTransactionsTab(): Boolean {
        return this.showTransactionsTab.value
    }

    fun updateTransactionsTab() {
        if (this.navController.currentDestination.isTransactionsLevelDestination()) {
            this.showTransactionsTab.value = true
            return
        }

        this.showTransactionsTab.value = false
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
}

