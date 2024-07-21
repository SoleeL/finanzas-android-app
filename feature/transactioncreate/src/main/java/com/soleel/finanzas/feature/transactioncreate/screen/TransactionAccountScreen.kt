package com.soleel.finanzas.feature.transactioncreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AccountCard
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.core.ui.template.CreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.getAccountUI
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.AccountsUiState
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import java.util.Date

@Composable
internal fun TransactionAccountRoute(
    onAcceptCancel: () -> Unit,
    fromAccountToType: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val AccountsUiState by viewModel.accountsUiState.collectAsStateWithLifecycle()
//    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionAccountScreen(
        modifier = Modifier,
        onAcceptCancel = onAcceptCancel,
        accountsUiState = AccountsUiState,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromAccountToType = fromAccountToType
    )
}

@Preview
@Composable
fun TransactionAccountScreenPreview() {
    TransactionAccountScreen(
        modifier = Modifier,
        onAcceptCancel = {},
        accountsUiState = AccountsUiState.Success(
            listOf(
                Account(
                    id = "1",
                    name = "Credito falabella",
                    amount = 300000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.CREDIT
                ),

                Account(
                    id = "2",
                    name = "Cuenta corriente falabella",
                    amount = 400000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.DEBIT
                ),

                Account(
                    id = "3",
                    name = "App Racional",
                    amount = 500000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.INVESTMENT
                )
            )
        ),
//        transactionUiCreate = TransactionUiCreate(),
        onTransactionCreateUiEvent = {},
        fromAccountToType = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionAccountScreen(
    modifier: Modifier,
    onAcceptCancel: () -> Unit,
    accountsUiState: AccountsUiState,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromAccountToType: () -> Unit
) {
    val showCancelAlert: MutableState<Boolean> = remember(calculation =  { mutableStateOf(false) })

    if (showCancelAlert.value) {
        CancelAlertDialog(
            onDismiss = { showCancelAlert.value = false },
            onConfirmation = {
                showCancelAlert.value = false
                onAcceptCancel()
            },
            dialogTitle = "¿Quieres volver atras?",
            dialogText = "Cancelaras la creacion de esta transaccion."
        )
    }

    BackHandler(
        enabled = true,
        onBack = { showCancelAlert.value = false == showCancelAlert.value }
    )

    Scaffold(
        topBar = {
            CreateTopAppBar(
                title= R.string.trasaction_create_title,
                subTitle = R.string.trasaction_account_top_app_bar_subtitle,
                onBackButton = { showCancelAlert.value = true }
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
                    .padding(top = it.calculateTopPadding()),
                content = {
                    when (accountsUiState) {
                        is AccountsUiState.Success -> SelectAccount(
                            accounts = accountsUiState.accounts,
//                            transactionUiCreate = transactionUiCreate,
                            onTransactionCreateUiEvent = onTransactionCreateUiEvent,
                            fromAccountToType = fromAccountToType
                        )

                        is AccountsUiState.Error -> {}
                        is AccountsUiState.Loading -> {}
                    }
                }
            )
        }
    )
}

@Composable
fun SelectAccount(
    accounts: List<Account>,
//    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromAccountToType: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = accounts,
                itemContent = { account ->

                    val currencyVisualTransformation by remember(calculation = {
                        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
                    })

                    val AccountAmount: String = currencyVisualTransformation
                        .filter(AnnotatedString(text = account.amount.toString()))
                        .text
                        .toString()

                    AccountCard(
                        accountUIValues = getAccountUI(
                            accountTypeEnum = account.type,
                            accountName=account.name,
                            accountAmount = AccountAmount
                        ),
                        onClick = {
                            onTransactionCreateUiEvent(
                                TransactionUiEvent.AccountChanged(
                                    account = account
                                )
                            )
                            fromAccountToType()
                        }
                    )
                }
            )
        }
    )
}