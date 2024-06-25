package com.soleel.finanzas.feature.transactioncreate.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateRoute
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.screen.TransactionAccountRoute
import com.soleel.finanzas.feature.transactioncreate.screen.TransactionAmountRoute
import com.soleel.finanzas.feature.transactioncreate.screen.TransactionCategoryRoute
import com.soleel.finanzas.feature.transactioncreate.screen.TransactionNameRoute
import com.soleel.finanzas.feature.transactioncreate.screen.TransactionTypeRoute


const val transactionCreateGraph = "transaction_create_graph"

const val transactionCreateRoute = "transaction_create_route"
const val transactionAccountRoute = "transaction__account_route"
const val transactionTypeRoute = "transaction_type_route"
const val transactionCategoryRoute = "transaction_category_route"
const val transactionNameRoute = "transaction_name_route"
const val transactionAmountRoute = "transaction_amount_route"

fun NavController.navigateToTransactionCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionCreateGraph, navOptions)
}

fun NavController.navigateToTransactionAccountRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionAccountRoute, navOptions)
}

fun NavController.navigateToTransactionTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionTypeRoute, navOptions)
}

fun NavController.navigateToTransactionCategoryRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionCategoryRoute, navOptions)
}

fun NavController.navigateToTransactionNameRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionNameRoute, navOptions)
}

fun NavController.navigateToTransactionAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionAmountRoute, navOptions)
}

fun NavGraphBuilder.transactionCreateGraph(
    navController: NavHostController,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    fromInitToAccount: () -> Unit,
    fromAccountToType: () -> Unit,
    fromTypeToCategory: () -> Unit,
    fromCategoryToName: () -> Unit,
    fromNameToAmount: () -> Unit
) {
    navigation(
        startDestination = transactionCreateRoute,
        route = transactionCreateGraph,
        builder = {
            // README: Es la ruta de carga de los datos, si esto falla, entonces no es necesario
            //  mostrar le modal de cancelar la creacion de la transaccion.
            transactionCreateRoute(
                navController = navController,
                showTransactionsTab = showTransactionsTab,
                showBottomBar = showBottomBar,
                showFloatingAddMenu = showFloatingAddMenu,
                onBackClick = onBackClick,
                fromInitToAccount = fromInitToAccount
            )
            transactionAccountRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                fromAccountToType = fromAccountToType
            )
            transactionTypeRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromTypeToCategory = fromTypeToCategory
            )
            transactionCategoryRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromCategoryToName = fromCategoryToName
            )
            transactionNameRoute(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            transactionAmountRoute(
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

fun NavGraphBuilder.transactionCreateRoute(
    navController: NavHostController,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    onBackClick: () -> Unit,
    fromInitToAccount: () -> Unit
) {
    composable(
        route = transactionCreateRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionCreateRoute(
                showTransactionsTab = showTransactionsTab,
                showBottomBar = showBottomBar,
                showFloatingAddMenu = showFloatingAddMenu,
                onBackClick = onBackClick,
                fromInitToAccount = fromInitToAccount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionAccountRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    fromAccountToType: () -> Unit
) {
    composable(
        route = transactionAccountRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionAccountRoute(
                onCancelClick = onCancelClick,
                fromAccountToType = fromAccountToType,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionTypeRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromTypeToCategory: () -> Unit,
) {
    composable(
        route = transactionTypeRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionTypeRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromTypeToCategory = fromTypeToCategory,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionCategoryRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromCategoryToName: () -> Unit,

    ) {
    composable(
        route = transactionCategoryRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionCategoryRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromCategoryToName = fromCategoryToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionNameRoute(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
) {
    composable(
        route = transactionNameRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionNameRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionAmountRoute(
    navController: NavHostController,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    composable(
        route = transactionAmountRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = transactionCreateGraph)
                }
            )

            val viewModel: TransactionCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            TransactionAmountRoute(
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