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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.data.transaction.model.Transaction
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
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "All Transactions List Screen",
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        content = {
            if (allTransactions.isEmpty()) {
                Text(
                    text = "No existen transacciones actualmente.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            } else {
                LazyColumn {
                    items(allTransactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    )
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Text(text = transaction.name)
}
