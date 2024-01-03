package com.soleel.createtransaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.createtransaction.CreateTransactionRoute


const val createTransactionRoute = "create_transaction_route"

fun NavController.navigateToCreateTransaction(navOptions: NavOptions? = null) {
    this.navigate(createTransactionRoute, navOptions)
}

fun NavGraphBuilder.createTransactionScreen(
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    composable(
        route = createTransactionRoute,
        content = {
            CreateTransactionRoute(
                onShowBottomBar = onShowBottomBar,
                onShowAddFloating = onShowAddFloating,
                onBackClick = onBackClick,
                onCancelClick = onCancelClick
            )
        }
    )
}

