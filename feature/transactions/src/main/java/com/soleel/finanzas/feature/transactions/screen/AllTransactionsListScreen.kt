package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.template.AllTransactionItem
import com.soleel.finanzas.core.ui.uivalues.TransactionUIValues
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.data.transaction.model.Transaction
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsUiState
import com.soleel.finanzas.feature.transactions.TransactionsViewModel

@Composable
internal fun AllTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val transactionsUiState: TransactionsUiState by viewModel.transactionsUiState.collectAsState()

    AllTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        transactionsUiState = transactionsUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent
    )
}

@Composable
fun AllTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    transactionsUiState: TransactionsUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "AllTransactionsListScreen: back press")
            finishApp(context)
        }
    )

    when (transactionsUiState) {
        is TransactionsUiState.Success -> AllTransactionsSuccessScreen(transactionsUiState.allTransactions)

        is TransactionsUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
fun AllTransactionsSuccessScreen(
    allTransactions: List<Transaction>
) {
    if (allTransactions.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            content = {
                Text(
                    text = "No existen transacciones actualmente.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        )
    } else {
        AllTransactionList(
            allTransactions = allTransactions
        )
    }
}

@Composable
fun AllTransactionList(
    allTransactions: List<Transaction>
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        content = {
            items(items = allTransactions) { transaction ->

                val transactionAmount: String = currencyVisualTransformation
                    .filter(AnnotatedString(text = transaction.amount.toString()))
                    .text
                    .toString()

                val transactionUIValues: TransactionUIValues = getTransactionUI(
                    paymentAccountTypeEnum = PaymentAccountTypeEnum.CREDIT,
                    paymentAccountName = "CMR Falabella",
                    paymentAccountAmount = "$280,000",
                    transactionType = TransactionTypeEnum.fromId(transaction.transactionType),
                    transactionCategory = TransactionCategoryEnum.fromId(transaction.categoryType),
                    transactionName = transaction.name,
                    transactionDate = TransactionFormatDateUseCase.provideAllTransactionStringDate(transaction.createAt),
                    transactionAmount = transactionAmount
                )

                AllTransactionItem(
                    transactionUIValues = transactionUIValues,
                    onClick = {}
                )
            }
        }
    )
}