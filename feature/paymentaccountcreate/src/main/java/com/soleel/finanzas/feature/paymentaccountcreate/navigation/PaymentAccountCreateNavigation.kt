package com.soleel.finanzas.feature.paymentaccountcreate.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.feature.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.finanzas.feature.paymentaccountcreate.screen.CreateSelectPaymentAccountTypeRoute
import com.soleel.finanzas.feature.paymentaccountcreate.screen.PaymentAccountAmountRoute
import com.soleel.finanzas.feature.paymentaccountcreate.screen.PaymentAccountNameRoute


const val paymentAccountCreateGraph = "payment_account_create_graph"

const val paymentAccountTypeRoute = "payment_account_type_route"
const val paymentAccountNameRoute = "payment_account_name_route"
const val paymentAccountAmountRoute = "enter_payment_account_amount_route"

fun NavController.navigateToPaymentAccountCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountCreateGraph, navOptions)
}

fun NavController.navigateToPaymentAccountTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountTypeRoute, navOptions)
}

fun NavController.navigateToPaymentAccountNameRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountNameRoute, navOptions)
}

fun NavController.navigateToPaymentAccountAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountAmountRoute, navOptions)
}

fun NavGraphBuilder.paymentAccountCreateGraph(
    navController: NavHostController,
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
        startDestination = paymentAccountTypeRoute,
        route = paymentAccountCreateGraph,
        builder = {
            paymentAccountTypeRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName
            )
            paymentAccountNameRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            paymentAccountAmountRoute(
                navController = navController,
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

fun NavGraphBuilder.paymentAccountTypeRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit
) {
    composable(
        route = paymentAccountTypeRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            CreateSelectPaymentAccountTypeRoute(
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.paymentAccountNameRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit
) {
    composable(
        route = paymentAccountNameRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            PaymentAccountNameRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.paymentAccountAmountRoute(
    navController: NavHostController,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    composable(
        route = paymentAccountAmountRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            PaymentAccountAmountRoute(
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