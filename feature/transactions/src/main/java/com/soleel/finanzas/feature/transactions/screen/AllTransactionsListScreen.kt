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
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.ui.template.AllTransactionItem
import com.soleel.finanzas.core.ui.uivalues.TransactionUIValues
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.domain.formatdate.AllTransactionFormatDateUseCase
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
            finishApp(context)
        }
    )

    when (transactionsUiState) {
        is TransactionsUiState.Success -> AllTransactionsSuccessScreen(transactionsUiState.allTransactionsWithAccount)

        is TransactionsUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
fun AllTransactionsSuccessScreen(
    allTransactionsWithAccount: List<TransactionWithAccount>
) {
    if (allTransactionsWithAccount.isEmpty()) {
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
            allTransactionWithAccount = allTransactionsWithAccount
        )
    }
}

@Composable
fun AllTransactionList(
    allTransactionWithAccount: List<TransactionWithAccount>
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        content = {
            items(items = allTransactionWithAccount) { transactionWithAccount ->

                val transactionAmount: String = currencyVisualTransformation
                    .filter(AnnotatedString(text = transactionWithAccount.transaction.amount.toString()))
                    .text
                    .toString()

                val accountAmount: String = currencyVisualTransformation
                    .filter(AnnotatedString(text = transactionWithAccount.account.amount.toString()))
                    .text
                    .toString()

                val transactionUIValues: TransactionUIValues = getTransactionUI(
                    accountTypeEnum = transactionWithAccount.account.type,
                    accountName = transactionWithAccount.account.name,
                    accountAmount = accountAmount,
                    transactionType = transactionWithAccount.transaction.type,
                    transactionCategory = transactionWithAccount.transaction.category,
                    transactionName = transactionWithAccount.transaction.name,
                    transactionDate = AllTransactionFormatDateUseCase(transactionWithAccount.transaction.createAt),
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