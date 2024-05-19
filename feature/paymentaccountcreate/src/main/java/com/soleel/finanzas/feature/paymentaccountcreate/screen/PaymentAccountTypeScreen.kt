package com.soleel.finanzas.feature.paymentaccountcreate.screen


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soleel.finanzas.feature.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.finanzas.feature.paymentaccountcreate.PaymentAccountUiCreate
import com.soleel.finanzas.feature.paymentaccountcreate.PaymentAccountUiEvent
import com.soleel.finanzas.feature.paymentaccountcreate.util.PaymentAccountCards
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.PaymentAccountCard
import com.soleel.finanzas.core.ui.template.PaymentAccountCreateTopAppBar
import com.soleel.finanzas.core.ui.util.PaymentAccountCardItem


@Composable
internal fun CreateSelectPaymentAccountTypeRoute(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit,
    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi: PaymentAccountUiCreate = viewModel.paymentAccountUiCreate

    CreateSelectPaymentAccountTypeScreen(
        modifier = modifier,

        onCancelClick = onCancelClick,

        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi,

        fromTypeToName = fromTypeToName
    )
}

@Preview
@Composable
fun CreateSelectPaymentAccountTypeScreenPreview() {
    CreateSelectPaymentAccountTypeScreen(
        modifier = Modifier,
        onCancelClick = {},
        paymentAccountCreateUi = PaymentAccountUiCreate(),
        onPaymentAccountCreateEventUi = {},
        fromTypeToName = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun CreateSelectPaymentAccountTypeScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit,
    fromTypeToName: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    Scaffold(
        topBar = {
            PaymentAccountCreateTopAppBar(
                subTitle = R.string.payment_account_type_top_app_bar_subtitle,
                onCancelClick = onCancelClick
            )
        },
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                content = {
//                    Button(
//                        onClick = { fromTypeToName() },
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(64.dp),
//                        enabled = 0 != paymentAccountUiCreate.type,
//                        content = { Text(text = "Avanzar a Name") }
//                    )
//                }
//            )
//        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
                    .padding(top = it.calculateTopPadding()),
                content = {
                    SelectPaymentAccountType(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi,
                        fromTypeToName = fromTypeToName
                    )
                }
            )
        }
    )
}

@Composable
fun SelectPaymentAccountType(
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit,
    fromTypeToName: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = PaymentAccountCards.cardsList,
                itemContent = { paymentAccountCard: PaymentAccountCardItem ->
                    PaymentAccountCard(
                        paymentAccountCardItem = paymentAccountCard,
                        onClick = {
                            onPaymentAccountCreateEventUi(
                                PaymentAccountUiEvent.TypeChanged(
                                    accountType = paymentAccountCard.type
                                )
                            )
                            fromTypeToName()
                        }
                    )
                }
            )
        }
    )
}