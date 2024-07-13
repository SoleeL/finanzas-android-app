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
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.getAccountUI
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.AccountsUiState
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import java.util.Date

@Composable
internal fun TransactionAccountRoute(
    onCancelClick: () -> Unit,
    fromAccountToType: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val AccountsUiState by viewModel.accountsUiState.collectAsStateWithLifecycle()
//    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionAccountScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        AccountsUiState = AccountsUiState,
//        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromAccountToType = fromAccountToType
    )
}

@Preview
@Composable
fun TransactionAccountScreenPreview() {
    TransactionAccountScreen(
        modifier = Modifier,
        onCancelClick = {},
        AccountsUiState = AccountsUiState.Success(
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
    onCancelClick: () -> Unit,
    AccountsUiState: AccountsUiState,
//    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromAccountToType: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_account_top_app_bar_subtitle,
                onClick = onCancelClick
            )
        },
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                content = {
//                    Button(
//                        onClick = { fromAccountToType() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
////                        enabled = 0 != AccountUiCreate.type,
//                        content = { Text(text = "Avanzar a seleccion de tipo") }
//                    )
//                }
//            )
//        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
                    .padding(top = it.calculateTopPadding()),
                content = {
                    when (AccountsUiState) {
                        is AccountsUiState.Success -> SelectAccount(
                            Accounts = AccountsUiState.accounts,
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
    Accounts: List<Account>,
//    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromAccountToType: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = Accounts,
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