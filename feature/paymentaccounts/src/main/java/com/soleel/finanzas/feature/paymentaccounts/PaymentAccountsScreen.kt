package com.soleel.finanzas.feature.paymentaccounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun AccountsRoute(
    modifier: Modifier = Modifier,
    viewModel: PaymentAccountsViewModel = hiltViewModel()
){
    AccountsScreen(modifier = modifier, viewModel = viewModel)
}

@Composable
fun AccountsScreen(
    modifier: Modifier,
    viewModel: PaymentAccountsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Accounts Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
