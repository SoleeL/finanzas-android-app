package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.TransactionsSummary
import com.soleel.finanzas.core.ui.theme.TransactionTypeExpenditureBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeIncomeBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeLetterColor
import com.soleel.finanzas.domain.formatdate.AllTransactionsGroupDateUseCase
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsSummaryUiState
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel
import java.util.Date

@Composable
internal fun TransactionsSummaryListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val transactionsSummaryUiState: TransactionsSummaryUiState by viewModel.transactionsSummaryUiState.collectAsState()

    TransactionsSummaryListScreen(
        modifier = modifier,
        finishApp = finishApp,
        transactionsSummaryUiState = transactionsSummaryUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent
    )
}

@Composable
private fun TransactionsSummaryListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    transactionsSummaryUiState: TransactionsSummaryUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            finishApp(context)
        }
    )

    when (transactionsSummaryUiState) {
        is TransactionsSummaryUiState.Success -> TransactionsSummarySuccessScreen(
            transactionsSummaryList = transactionsSummaryUiState.transactionsSummaryList
        )

        is TransactionsSummaryUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsSummaryUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
private fun TransactionsSummarySuccessScreen(
    transactionsSummaryList: List<TransactionsSummary>
) {
    if (transactionsSummaryList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), content = {
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
        TransactionsSummaryList(transactionsSummaryList = transactionsSummaryList)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsSummaryList(
    transactionsSummaryList: List<TransactionsSummary>
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 72.dp),
        content = {
            transactionsSummaryList.forEach(
                action = { summary ->
                    stickyHeader(
                        content = {
                            TransactionsGroupDate(date = summary.date)
                        }
                    )

                    items(
                        items = summary.transactions,
                        itemContent = { transactionSummary ->
                            if (0 != transactionSummary.amount) {
                                val summaryExpenditureAmount: String =
                                    currencyVisualTransformation.filter(
                                        AnnotatedString(
                                            text = transactionSummary.amount.toString()
                                        )
                                    ).text.toString()

                                val typeColor: Color =
                                    if (TransactionTypeEnum.INCOME == transactionSummary.type)
                                        TransactionTypeIncomeBackgroundColor else TransactionTypeExpenditureBackgroundColor

                                TransactionSummaryItem(
                                    typeIcon = transactionSummary.type.icon,
                                    typeName = transactionSummary.type.value,
                                    typeColor = typeColor,
                                    name = transactionSummary.name,
                                    amount = summaryExpenditureAmount,
                                    onClick = {}
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun TransactionsGroupDate(
    date: Date
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalArrangement = Arrangement.Start,
        content = {
            Text(
                text = AllTransactionsGroupDateUseCase(date),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun TransactionSummaryItem(
    typeIcon: Int,
    typeName: String,
    typeColor: Color,
    name: String,
    amount: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            TransactionTypeRow(
                transactionTypeIcon = typeIcon,
                transactionTypeName = typeName,
                transactionTypeColor = typeColor
            )
            TransactionDetailRow(
                transactionName = name,
                transactionAmount = amount
            )
        }
    )
}

@Composable
private fun TransactionTypeRow(
    transactionTypeIcon: Int,
    transactionTypeName: String,
    transactionTypeColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = transactionTypeColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = transactionTypeIcon),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(16.dp),
                        tint = TransactionTypeLetterColor
                    )
                    Text(
                        text = transactionTypeName,
                        modifier = Modifier.padding(start = 8.dp),
                        color = TransactionTypeLetterColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    )
}

@Composable
private fun TransactionDetailRow(
    transactionName: String,
    transactionAmount: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(content = {
                        Text(
                            text = transactionName, style = MaterialTheme.typography.titleMedium
                        )
                    })

                    Column(
                        horizontalAlignment = Alignment.End,
                        content = {
                            Text(
                                text = transactionAmount,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                }
            )
        }
    )
}