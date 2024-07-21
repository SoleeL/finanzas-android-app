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
import com.soleel.finanzas.feature.accountcreate.screen.AccountAmountRoute
import com.soleel.finanzas.feature.accountcreate.screen.AccountNameRoute
import com.soleel.finanzas.feature.accountcreate.screen.CreateSelectAccountTypeRoute


const val accountCreateGraph = "account_create"

const val accountTypeSelectionRoute = "type"
const val accountNameInputRoute = "name"
const val accountAmountInputRoute = "amount"

fun NavController.navigateToAccountCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(accountCreateGraph, navOptions)
}

fun NavController.navigateToAccountTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(accountTypeSelectionRoute, navOptions)
}

fun NavController.navigateToAccountNameRoute(navOptions: NavOptions? = null) {
    this.navigate(accountNameInputRoute, navOptions)
}

fun NavController.navigateToAccountAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(accountAmountInputRoute, navOptions)
}

fun NavGraphBuilder.accountCreateGraph(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromTypeToName: () -> Unit,
    fromNameToAmount: () -> Unit,
    onAccountSaved: () -> Unit,
) {
    navigation(
        startDestination = accountTypeSelectionRoute,
        route = accountCreateGraph,
        builder = {
            accountTypeRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                fromTypeToName = fromTypeToName
            )
            accountNameRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            accountAmountRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                onAccountSaved = onAccountSaved
            )
        }
    )
}

fun NavGraphBuilder.accountTypeRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    fromTypeToName: () -> Unit
) {
    composable(
        route = accountTypeSelectionRoute,
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
                onAcceptCancel = onAcceptCancel,
                fromTypeToName = fromTypeToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.accountNameRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit
) {
    composable(
        route = accountNameInputRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.accountAmountRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    onAccountSaved: () -> Unit
) {
    composable(
        route = accountAmountInputRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                onAccountSaved = onAccountSaved,
                viewModel = viewModel
            )
        }
    )
}