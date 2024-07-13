package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
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
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.core.ui.template.AllTransactionItem
import com.soleel.finanzas.core.ui.uivalues.TransactionUIValues
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.domain.formatdate.AllTransactionFormatDateUseCase
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsGroupUiState
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel

@Composable
internal fun AllTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val allTransactionsGroupUiState: TransactionsGroupUiState by viewModel.allTransactionsUiState.collectAsState()

    AllTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        allTransactionsGroupUiState = allTransactionsGroupUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent
    )
}

@Composable
fun AllTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    allTransactionsGroupUiState: TransactionsGroupUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            finishApp(context)
        }
    )

    when (allTransactionsGroupUiState) {
        is TransactionsGroupUiState.Success -> AllTransactionsSuccessScreen(allTransactionsGroupUiState.transactionsGroup)

        is TransactionsGroupUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsGroupUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
fun AllTransactionsSuccessScreen(
    allTransactionsGroup: List<TransactionsGroup>
) {
    if (allTransactionsGroup.isEmpty()) {
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
            allTransactionsGroup = allTransactionsGroup
        )
    }
}

@Composable
fun AllTransactionList(
    allTransactionsGroup: List<TransactionsGroup>
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        content = {
            items(items = allTransactionsGroup) { transactionsGroup ->

                val transactionAmount: String = currencyVisualTransformation
                    .filter(AnnotatedString(text = transactionsGroup.transaction.amount.toString()))
                    .text
                    .toString()

                val accountAmount: String = currencyVisualTransformation
                    .filter(AnnotatedString(text = transactionsGroup.account.amount.toString()))
                    .text
                    .toString()

                val transactionUIValues: TransactionUIValues = getTransactionUI(
                    accountTypeEnum = transactionsGroup.account.type,
                    accountName = transactionsGroup.account.name,
                    accountAmount = accountAmount,
                    transactionType = transactionsGroup.transaction.type,
                    transactionCategory = transactionsGroup.transaction.category,
                    transactionName = transactionsGroup.transaction.name,
                    transactionDate = AllTransactionFormatDateUseCase(transactionsGroup.transaction.createAt),
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