package com.soleel.finanzas.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.data.transaction.model.Transaction


@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUi: HomeUiState by viewModel.homeUiState.collectAsState()

    HomeScreen(
        modifier = modifier, homeUi = homeUi
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier.background(Color.White),
        homeUi = HomeUiState(
            isPaymentAccountLoading = false,
            isPaymentAccountSuccess = true,
            isTransactionLoading = false,
            isTransactionSuccess = true
        )
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier, homeUi: HomeUiState
) {

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), content = {

        if (homeUi.isPaymentAccountLoading && homeUi.isTransactionLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        if (homeUi.isPaymentAccountSuccess && homeUi.isTransactionSuccess) {
            val transactions: List<Transaction> = homeUi.itemsTransaction

            if (transactions.isEmpty()) {
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
                    items(transactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    })
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Text(text = transaction.name)
}