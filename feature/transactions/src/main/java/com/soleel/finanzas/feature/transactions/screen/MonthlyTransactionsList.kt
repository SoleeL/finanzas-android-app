package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsSumUiState
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel

@Composable
internal fun MonthlyTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val monthlyTransactionsSumUiState: TransactionsSumUiState by viewModel.monthlyTransactionsUiState.collectAsState()

    MonthlyTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        monthlyTransactionsSumUiState = monthlyTransactionsSumUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent
    )
}

@Composable
fun MonthlyTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    monthlyTransactionsSumUiState: TransactionsSumUiState,
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

    when (monthlyTransactionsSumUiState) {
        is TransactionsSumUiState.Success -> MonthlyTransactionsSuccessScreen()

        is TransactionsSumUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsSumUiState.Loading -> TransactionsLoadingScreen()
    }

}

@Composable
fun MonthlyTransactionsSuccessScreen() {
    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "MonthlyTransactionsSuccessScreen: back press")
            // TODO:
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Monthly Transactions List Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

