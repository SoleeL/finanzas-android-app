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
import com.soleel.finanzas.feature.transactions.SummaryTransactionsUiState
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination

@Composable
internal fun SummaryPeriodTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val summaryTransactionsUiState: SummaryTransactionsUiState by viewModel.summaryTransactionsUiState.collectAsState()

    SummaryPeriodTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        summaryTransactionsUiState = summaryTransactionsUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent,
        summaryPeriod = viewModel.summaryPeriod
    )
}


@Composable
fun SummaryPeriodTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    summaryTransactionsUiState: SummaryTransactionsUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit,
    summaryPeriod: TransactionsLevelDestination
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "AllTransactionsListScreen: back press")
            finishApp(context)
        }
    )

    when (summaryTransactionsUiState) {
        is SummaryTransactionsUiState.Success -> SummaryPeriodTransactionsSuccessScreen(
            summaryPeriod = summaryPeriod
        )

        is SummaryTransactionsUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is SummaryTransactionsUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
fun SummaryPeriodTransactionsSuccessScreen(
    summaryPeriod: TransactionsLevelDestination
) {
    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "SummaryPeriodTransactionsSuccessScreen: back press")
            // TODO:
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        content = {
            Text(
                text = "$summaryPeriod Transactions List Screen",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    )
}

