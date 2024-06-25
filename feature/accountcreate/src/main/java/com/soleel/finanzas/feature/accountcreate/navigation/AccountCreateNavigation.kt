package com.soleel.finanzas.feature.accountcreate.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.feature.accountcreate.AccountCreateViewModel
import com.soleel.finanzas.feature.accountcreate.screen.CreateSelectAccountTypeRoute
import com.soleel.finanzas.feature.accountcreate.screen.AccountAmountRoute
import com.soleel.finanzas.feature.accountcreate.screen.AccountNameRoute


const val accountCreateGraph = "_account_create_graph"

const val accountTypeRoute = "_account_type_route"
const val accountNameRoute = "_account_name_route"
const val accountAmountRoute = "enter__account_amount_route"

fun NavController.navigateToAccountCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(accountCreateGraph, navOptions)
}

fun NavController.navigateToAccountTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(accountTypeRoute, navOptions)
}

fun NavController.navigateToAccountNameRoute(navOptions: NavOptions? = null) {
    this.navigate(accountNameRoute, navOptions)
}

fun NavController.navigateToAccountAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(accountAmountRoute, navOptions)
}

fun NavGraphBuilder.accountCreateGraph(
    navController: NavHostController,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    fromTypeToName: () -> Unit,
    fromNameToAmount: () -> Unit,
) {
    navigation(
        startDestination = accountTypeRoute,
        route = accountCreateGraph,
        builder = {
            accountTypeRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName
            )
            accountNameRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            accountAmountRoute(
                navController = navController,
                showTransactionsTab = showTransactionsTab,
                showBottomBar = showBottomBar,
                showFloatingAddMenu = showFloatingAddMenu,
                hideExtendAddMenu = hideExtendAddMenu,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                onSaveClick = onSaveClick
            )
        }
    )
}

fun NavGraphBuilder.accountTypeRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit
) {
    composable(
        route = accountTypeRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = accountCreateGraph)
                }
            )

            val viewModel: AccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            CreateSelectAccountTypeRoute(
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.accountNameRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit
) {
    composable(
        route = accountNameRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = accountCreateGraph)
                }
            )

            val viewModel: AccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            AccountNameRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.accountAmountRoute(
    navController: NavHostController,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    composable(
        route = accountAmountRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = accountCreateGraph)
                }
            )

            val viewModel: AccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            AccountAmountRoute(
                showTransactionsTab = showTransactionsTab,
                showBottomBar = showBottomBar,
                showFloatingAddMenu = showFloatingAddMenu,
                hideExtendAddMenu = hideExtendAddMenu,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                onSaveClick = onSaveClick,
                viewModel = viewModel
            )
        }
    )
}