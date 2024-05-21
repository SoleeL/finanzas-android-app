package com.soleel.finanzas.feature.transactions.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.feature.transactions.TransactionsUiState
import com.soleel.finanzas.feature.transactions.TransactionsViewModel

@Composable
internal fun TransactionsListRoute(
    modifier: Modifier = Modifier,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val transactionUi: TransactionsUiState by viewModel.transactionsUiState.collectAsState()

    TransactionsListScreen()
}

@Composable
fun TransactionsListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Transactions List Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }




//    Column(modifier = modifier
//        .fillMaxSize()
//        .wrapContentSize(Alignment.Center), content = {
//
//        if (transactionUi.isPaymentAccountLoading && transactionUi.isTransactionLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
//            )
//        }
//
//        if (transactionUi.isPaymentAccountSuccess && transactionUi.isTransactionSuccess) {
//            val transactions: List<Transaction> = transactionUi.itemsTransaction
//
//            if (transactions.isEmpty()) {
//                Text(
//                    text = "No existen transacciones actualmente.",
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp
//                )
//            } else {
//                LazyColumn {
//                    items(transactions) { transaction ->
//                        TransactionItem(transaction)
//                    }
//                }
//            }
//        }
//    })
//
//    @Composable
//    fun TransactionItem(transaction: Transaction) {
//        Text(text = transaction.name)
//    }
}
