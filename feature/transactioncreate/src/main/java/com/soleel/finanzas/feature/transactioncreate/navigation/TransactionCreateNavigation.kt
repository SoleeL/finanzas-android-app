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


const val transactionCreateGraph = "transaction_create"

const val transactionCreateRoute = "init"
const val transactionSelectAccountRoute = "select_account"
const val transactionSelectTypeRoute = "select_type"
const val transactionSelectCategoryRoute = "select_category"
const val transactionInputNameRoute = "input_name"
const val transactionInputAmountRoute = "input_amount"

fun NavController.navigateToTransactionCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionCreateGraph, navOptions)
}

fun NavController.navigateToTransactionAccountRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionSelectAccountRoute, navOptions)
}

fun NavController.navigateToTransactionTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionSelectTypeRoute, navOptions)
}

fun NavController.navigateToTransactionCategoryRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionSelectCategoryRoute, navOptions)
}

fun NavController.navigateToTransactionNameRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionInputNameRoute, navOptions)
}

fun NavController.navigateToTransactionAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(transactionInputAmountRoute, navOptions)
}

fun NavGraphBuilder.transactionCreateGraph(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromInitToAccount: () -> Unit,
    fromAccountToType: () -> Unit,
    fromTypeToCategory: () -> Unit,
    fromCategoryToName: () -> Unit,
    fromNameToAmount: () -> Unit,
    onTransactionSaved: () -> Unit
) {
    navigation(
        startDestination = transactionCreateRoute,
        route = transactionCreateGraph,
        builder = {
            // README: Es la ruta de carga de los datos, si esto falla, entonces no es necesario
            //  mostrar el modal de cancelar la creacion de la transaccion, solo volver a la vista
            //  anterior en el backstack.
            transactionCreateRoute(
                navController = navController,
                onBackClick = onAcceptCancel,
                fromInitToAccount = fromInitToAccount
            )
            transactionAccountRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                fromAccountToType = fromAccountToType
            )
            transactionTypeRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromTypeToCategory = fromTypeToCategory
            )
            transactionCategoryRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromCategoryToName = fromCategoryToName
            )
            transactionNameRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            transactionAmountRoute(
                navController = navController,
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                onTransactionSaved = onTransactionSaved
            )
        }
    )
}

fun NavGraphBuilder.transactionCreateRoute(
    navController: NavHostController,
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
                onBackClick = onBackClick,
                fromInitToAccount = fromInitToAccount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionAccountRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    fromAccountToType: () -> Unit
) {
    composable(
        route = transactionSelectAccountRoute,
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
                onAcceptCancel = onAcceptCancel,
                fromAccountToType = fromAccountToType,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionTypeRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromTypeToCategory: () -> Unit,
) {
    composable(
        route = transactionSelectTypeRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromTypeToCategory = fromTypeToCategory,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionCategoryRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromCategoryToName: () -> Unit
) {
    composable(
        route = transactionSelectCategoryRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromCategoryToName = fromCategoryToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionNameRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
) {
    composable(
        route = transactionInputNameRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.transactionAmountRoute(
    navController: NavHostController,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    onTransactionSaved: () -> Unit,
) {
    composable(
        route = transactionInputAmountRoute,
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
                onAcceptCancel = onAcceptCancel,
                onBackClick = onBackClick,
                onTransactionSaved = onTransactionSaved,
                viewModel = viewModel
            )
        }
    )
}