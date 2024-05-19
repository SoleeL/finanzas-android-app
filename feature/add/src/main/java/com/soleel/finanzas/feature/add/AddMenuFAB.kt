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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.ui.R


@Preview
@Composable
fun FabMenuHidePreview() {
    AddMenuFAB(
        hideFloatingAddMenu = {},
        shouldShowExtendAddMenu = false,
        showExtendAddMenu = {},
        hideExtendAddMenu = {},
        hideBottomBar = {},
        toCreatePaymentAccount = {},
        toCreateTransaction = {},
        viewModel = AddMenuFABViewModelMock()
    )
}

@Preview
@Composable
fun FabMenuShowPreview() {
    AddMenuFAB(
        hideFloatingAddMenu = {},
        shouldShowExtendAddMenu = true,
        showExtendAddMenu = {},
        hideExtendAddMenu = {},
        hideBottomBar = {},
        toCreatePaymentAccount = {},
        toCreateTransaction = {},
        viewModel = AddMenuFABViewModelMock()
    )
}

@Composable
fun AddMenuFAB(
    hideFloatingAddMenu: () -> Unit,
    shouldShowExtendAddMenu: Boolean,
    showExtendAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    hideBottomBar: () -> Unit,
    toCreatePaymentAccount: () -> Unit,
    toCreateTransaction: () -> Unit,
    viewModel: AddMenuFABViewModel = hiltViewModel()
) {
    val paymentAccountsUiState: AddUiState by viewModel.addUiState.collectAsState()

//    val statusMenu: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(
        content = {
            if (shouldShowExtendAddMenu) {
                Column(
                    horizontalAlignment = Alignment.End,
                    content = {
                        if (false == paymentAccountsUiState.isPaymentAccountEmpty) {
                            ItemMenuFAB(
                                onClick = {
                                    toCreateTransaction()
                                    hideFloatingAddMenu()
                                    hideBottomBar()
                                },
                                icon = R.drawable.ic_add_transaction,
                                text = "Crear transacción"
                            )
                        }
                        ItemMenuFAB(
                            onClick = {
                                toCreatePaymentAccount()
                                hideFloatingAddMenu()
                                hideBottomBar()
                            },
                            icon = R.drawable.ic_add_account,
                            text = "Crear cuenta",
                        )
                    }
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
fun ItemMenuFAB(
    onClick: () -> Unit,
    icon: Int,
    text: String
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(bottom = 10.dp),
        text = { Text(text = text) },
        icon = { Icon(imageVector = ImageVector.vectorResource(icon), contentDescription = null) },
        onClick = { onClick() },
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
    )
}