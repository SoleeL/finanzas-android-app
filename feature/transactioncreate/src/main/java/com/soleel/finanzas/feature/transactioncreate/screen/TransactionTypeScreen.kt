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
import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
import com.soleel.finanzas.core.common.constants.TransactionTypeConstant
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiCreate
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.TransactionCard
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.util.getPaymentAccountCard
import com.soleel.finanzas.core.ui.util.getTransactionTypeCard


@Composable
internal fun TransactionTypeRoute(
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromTypeToCategory: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionTypeScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromTypeToCategory = fromTypeToCategory
    )
}

@Preview
@Composable
fun TransactionTypeScreenPreview() {
    TransactionTypeScreen(modifier = Modifier,
        onCancelClick = {},
        onBackClick = {},
        transactionUiCreate = TransactionUiCreate(
            PaymentAccount(
                id = "2",
                name = "Cuenta corriente falabella",
                amount = 400000,
                createAt = 1708709787983L,
                updatedAt = 1708709787983L,
                accountType = PaymentAccountTypeConstant.CREDIT
            )
        ),
        onTransactionCreateUiEvent = {},
        fromTypeToCategory = {})
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionTypeScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromTypeToCategory: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(topBar = {
        TransactionCreateTopAppBar(
            subTitle = R.string.trasaction_type_top_app_bar_subtitle, onClick = onCancelClick
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
//                        onClick = { fromTypeToCategory() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
////                        enabled = 0 != paymentAccountUiCreate.type,
//                        content = { Text(text = "Avanzar a seleccion de categoria") }
//                    )
//                }
//            )
//        },
        content = {
            val currencyVisualTransformation by remember(calculation = {
                mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
            })

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
                content = {

                    SelectTransactionType(
                        transactionUiCreate = transactionUiCreate,
                        onTransactionCreateUiEvent = onTransactionCreateUiEvent,
                        currencyVisualTransformation = currencyVisualTransformation,
                        fromTypeToCategory = fromTypeToCategory
                    )
                }
            )
        }
    )
}

@Composable
fun SelectTransactionType(
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    currencyVisualTransformation: CurrencyVisualTransformation,
    fromTypeToCategory: () -> Unit
) {

    val transactionTypes: List<Pair<Int, String>> = remember(calculation = {
        TransactionTypeConstant.idToValueList
    })

    val paymentAccountAmount: String = currencyVisualTransformation
        .filter(AnnotatedString(text = transactionUiCreate.paymentAccount.amount.toString()))
        .text
        .toString()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = transactionTypes,
                itemContent = { transactionType ->
                    TransactionCard(
                        paymentAccountCardItem = getPaymentAccountCard(
                            paymentAccountType = transactionUiCreate.paymentAccount.accountType,
                            paymentAccountTypeName = transactionUiCreate.paymentAccount.name,
                            amount = paymentAccountAmount
                        ),
                        transactionTypeCardItem = getTransactionTypeCard(
                            transactionType = transactionType.first
                        ),
                        onClick = {
                            onTransactionCreateUiEvent(
                                TransactionUiEvent.TransactionTypeChanged(
                                    transactionType = transactionType.first
                                )
                            )
                            fromTypeToCategory()
                        }
                    )
                }
            )
        }
    )
}