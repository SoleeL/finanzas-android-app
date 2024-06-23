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
import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.PaymentAccountCard
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.getPaymentAccountUI
import com.soleel.finanzas.core.model.PaymentAccount
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.PaymentAccountsUiState
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent

@Composable
internal fun TransactionPaymentAccountRoute(
    onCancelClick: () -> Unit,
    fromPaymentAccountToType: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val paymentAccountsUiState by viewModel.paymentAccountsUiState.collectAsStateWithLifecycle()
//    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionPaymentAccountScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        paymentAccountsUiState = paymentAccountsUiState,
//        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromPaymentAccountToType = fromPaymentAccountToType
    )
}

@Preview
@Composable
fun TransactionPaymentAccountScreenPreview() {
    TransactionPaymentAccountScreen(
        modifier = Modifier,
        onCancelClick = {},
        paymentAccountsUiState = PaymentAccountsUiState.Success(
            listOf(
                com.soleel.finanzas.core.model.PaymentAccount(
                    id = "1",
                    name = "Credito falabella",
                    amount = 300000,
                    createAt = 1708709787983L,
                    updatedAt = 1708709787983L,
                    accountType = PaymentAccountTypeEnum.CREDIT.id
                ),

                com.soleel.finanzas.core.model.PaymentAccount(
                    id = "2",
                    name = "Cuenta corriente falabella",
                    amount = 400000,
                    createAt = 1708709787983L,
                    updatedAt = 1708709787983L,
                    accountType = PaymentAccountTypeEnum.DEBIT.id
                ),

                com.soleel.finanzas.core.model.PaymentAccount(
                    id = "3",
                    name = "App Racional",
                    amount = 500000,
                    createAt = 1708709787983L,
                    updatedAt = 1708709787983L,
                    accountType = PaymentAccountTypeEnum.INVESTMENT.id
                )
            )
        ),
//        transactionUiCreate = TransactionUiCreate(),
        onTransactionCreateUiEvent = {},
        fromPaymentAccountToType = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionPaymentAccountScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    paymentAccountsUiState: PaymentAccountsUiState,
//    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromPaymentAccountToType: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_payment_account_top_app_bar_subtitle,
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
//                        onClick = { fromPaymentAccountToType() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
////                        enabled = 0 != paymentAccountUiCreate.type,
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
                    when (paymentAccountsUiState) {
                        is PaymentAccountsUiState.Success -> SelectPaymentAccount(
                            paymentAccounts = paymentAccountsUiState.paymentAccounts,
//                            transactionUiCreate = transactionUiCreate,
                            onTransactionCreateUiEvent = onTransactionCreateUiEvent,
                            fromPaymentAccountToType = fromPaymentAccountToType
                        )

                        is PaymentAccountsUiState.Error -> {}
                        is PaymentAccountsUiState.Loading -> {}
                    }
                }
            )
        }
    )
}

@Composable
fun SelectPaymentAccount(
    paymentAccounts: List<com.soleel.finanzas.core.model.PaymentAccount>,
//    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromPaymentAccountToType: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = paymentAccounts,
                itemContent = { paymentAccount ->

                    val currencyVisualTransformation by remember(calculation = {
                        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
                    })

                    val paymentAccountAmount: String = currencyVisualTransformation
                        .filter(AnnotatedString(text = paymentAccount.amount.toString()))
                        .text
                        .toString()

                    PaymentAccountCard(
                        paymentAccountUIValues = getPaymentAccountUI(
                            paymentAccountTypeEnum = PaymentAccountTypeEnum.fromId(paymentAccount.accountType),
                            paymentAccountName=paymentAccount.name,
                            paymentAccountAmount = paymentAccountAmount
                        ),
                        onClick = {
                            onTransactionCreateUiEvent(
                                TransactionUiEvent.PaymentAccountChanged(
                                    paymentAccount = paymentAccount
                                )
                            )
                            fromPaymentAccountToType()
                        }
                    )
                }
            )
        }
    )
}