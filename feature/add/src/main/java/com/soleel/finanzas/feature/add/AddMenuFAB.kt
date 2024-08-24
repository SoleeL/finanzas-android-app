package com.soleel.finanzas.feature.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.ui.R


@Preview
@Composable
fun FabMenuHidePreview() {
    AddMenuFAB(
        shouldShowExtendAddMenu = false,
        showExtendAddMenu = {},
        hideExtendAddMenu = {},
        onNavigateToCreateAccount = {},
        onNavigateToCreateTransaction = {},
        viewModel = AddMenuFABViewModelMock()
    )
}

@Preview
@Composable
fun FabMenuShowPreview() {
    AddMenuFAB(
        shouldShowExtendAddMenu = true,
        showExtendAddMenu = {},
        hideExtendAddMenu = {},
        onNavigateToCreateAccount = {},
        onNavigateToCreateTransaction = {},
        viewModel = AddMenuFABViewModelMock()
    )
}

@Composable
fun AddMenuFAB(
    shouldShowExtendAddMenu: Boolean,
    showExtendAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onNavigateToCreateAccount: () -> Unit,
    onNavigateToCreateTransaction: () -> Unit,
    viewModel: AddMenuFABViewModel = hiltViewModel()
) {
    val accountsUiState: AddUiState by viewModel.addUiState.collectAsStateWithLifecycle()

    Column(
        content = {
            if (shouldShowExtendAddMenu) {
                ExtendMenuFAB(
                    isAccountsEmpty = accountsUiState.isAccountsEmpty,
                    onNavigateToCreateAccount = onNavigateToCreateAccount,
                    onNavigateToCreateTransaction = onNavigateToCreateTransaction
                )
            }

            FloatingActionButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { if (shouldShowExtendAddMenu) hideExtendAddMenu() else showExtendAddMenu() },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                content = {
                    Icon(
                        imageVector = if (shouldShowExtendAddMenu) Icons.Filled.Clear else Icons.Filled.Add,
                        contentDescription = "Agregar"
                    )
                }
            )

        }
    )
}

@Composable
fun ExtendMenuFAB(
    isAccountsEmpty: Boolean,
    onNavigateToCreateAccount: () -> Unit,
    onNavigateToCreateTransaction: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
        content = {
            if (false == isAccountsEmpty) {
                ItemMenuFAB(
                    onClick = onNavigateToCreateTransaction,
                    icon = R.drawable.ic_add_transaction,
                    text = "Crear transacción"
                )
            }
            ItemMenuFAB(
                onClick = onNavigateToCreateAccount,
                icon = R.drawable.ic_add_account,
                text = "Crear cuenta",
            )
        }
    )
}

@Composable
fun ItemMenuFAB(
    onClick: () -> Unit,
    icon: Int,
    text: String
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(bottom = 10.dp),
        text = { Text(text = text) },
        icon = { Icon(imageVector = ImageVector.vectorResource(icon), contentDescription = null) },
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    )
}