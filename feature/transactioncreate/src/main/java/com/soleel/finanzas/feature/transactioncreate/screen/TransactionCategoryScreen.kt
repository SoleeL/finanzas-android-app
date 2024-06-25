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
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.TransactionCard
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiCreate
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import java.util.Date


@Composable
internal fun TransactionCategoryRoute(
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromCategoryToName: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionCategoryScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromCategoryToName = fromCategoryToName
    )
}

@Preview
@Composable
fun TransactionCategoryScreenPreview() {
    TransactionCategoryScreen(
        modifier = Modifier,
        onCancelClick = {},
        onBackClick = {},
        transactionUiCreate = TransactionUiCreate(
            account = Account(
                id = "2",
                name = "Cuenta corriente falabella",
                amount = 400000,
                createAt = Date(),
                updatedAt = Date(),
                type = AccountTypeEnum.CREDIT
            ),
            transactionType = TransactionTypeEnum.EXPENDITURE.id
        ),
        onTransactionCreateUiEvent = {},
        fromCategoryToName = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionCategoryScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromCategoryToName: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_category_top_app_bar_subtitle,
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
//                        onClick = { fromCategoryToName() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
////                        enabled = 0 != AccountUiCreate.type,
//                        content = { Text(text = "Avanzar a ingresar nombre") }
//                    )
//                }
//            )
//        },
        content = {

            val currencyVisualTransformation by remember(calculation = {
                mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
            })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    SelectTransactionCategory(
                        transactionUiCreate = transactionUiCreate,
                        onTransactionCreateUiEvent = onTransactionCreateUiEvent,
                        currencyVisualTransformation = currencyVisualTransformation,
                        fromCategoryToName = fromCategoryToName
                    )
                }
            )
        }
    )
}

@Composable
fun SelectTransactionCategory(
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    currencyVisualTransformation: CurrencyVisualTransformation,
    fromCategoryToName: () -> Unit
) {

    val transactionCategories: List<TransactionCategoryEnum> = remember(calculation = {
        TransactionCategoryEnum.getTransactionCategories(
            transactionType = TransactionTypeEnum.fromId(transactionUiCreate.transactionType),
            accountType = transactionUiCreate.account.type
        )
    })

    val AccountAmount: String = currencyVisualTransformation
        .filter(AnnotatedString(text = transactionUiCreate.account.amount.toString()))
        .text
        .toString()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = transactionCategories,
                itemContent = { transactionCategory ->
                    TransactionCard(
                        transactionUIValues = getTransactionUI(
                            accountTypeEnum = transactionUiCreate.account.type,
                            accountName = transactionUiCreate.account.name,
                            accountAmount = AccountAmount,
                            transactionType = TransactionTypeEnum.fromId(transactionUiCreate.transactionType),
                            transactionCategory = transactionCategory
                        ),
                        onClick = {
                            onTransactionCreateUiEvent(
                                TransactionUiEvent.TransactionCategoryChanged(
                                    transactionCategory = transactionCategory.id
                                )
                            )
                            fromCategoryToName()
                        }
                    )
                }
            )
        }
    )
}