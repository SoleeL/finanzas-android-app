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
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.TransactionCard
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiCreate
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import java.util.Date


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
            Account(
                id = "2",
                name = "Cuenta corriente falabella",
                amount = 400000,
                createAt = Date(),
                updatedAt = Date(),
                type = AccountTypeEnum.CREDIT
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
////                        enabled = 0 != AccountUiCreate.type,
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

    val transactionTypes: List<TransactionTypeEnum> = remember(calculation = {
        TransactionTypeEnum.entries
    })

    val accountAmount: String = currencyVisualTransformation
        .filter(AnnotatedString(text = transactionUiCreate.account.amount.toString()))
        .text
        .toString()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = transactionTypes,
                itemContent = { transactionType ->
                    TransactionCard(
                        transactionUIValues = getTransactionUI(
                            accountTypeEnum = transactionUiCreate.account.type,
                            accountName = transactionUiCreate.account.name,
                            accountAmount = accountAmount,
                            transactionType = TransactionTypeEnum.fromId(transactionType.id)
                        ),
                        onClick = {
                            onTransactionCreateUiEvent(
                                TransactionUiEvent.TransactionTypeChanged(
                                    transactionType = transactionType.id
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
