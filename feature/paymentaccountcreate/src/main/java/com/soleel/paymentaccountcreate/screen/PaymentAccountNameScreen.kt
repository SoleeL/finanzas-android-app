package com.soleel.paymentaccountcreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.paymentaccountcreate.PaymentAccountUiCreate
import com.soleel.paymentaccountcreate.PaymentAccountUiEvent
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.PaymentAccountCard
import com.soleel.finanzas.core.ui.template.PaymentAccountCreateTopAppBar
import com.soleel.finanzas.core.ui.util.PaymentAccountCardItem
import com.soleel.finanzas.core.ui.util.getPaymentAccountCard
import com.soleel.finanzas.domain.validation.validator.NameValidator


@Composable
internal fun PaymentAccountNameRoute(
    modifier: Modifier = Modifier,

    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,

    fromNameToAmount: () -> Unit,

    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi = viewModel.paymentAccountUiCreate

    PaymentAccountNameScreen(
        modifier = modifier,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi,
        fromNameToAmount = fromNameToAmount
    )
}

@Preview
@Composable
internal fun PaymentAccountNameScreenPreview() {
    PaymentAccountNameScreen(
        modifier = Modifier,
        onBackClick = {},
        onCancelClick = {},
        paymentAccountCreateUi = PaymentAccountUiCreate(
            type = PaymentAccountTypeConstant.CREDIT,
            name = "Inversion en bolsa",
        ),
        onPaymentAccountCreateEventUi = {},
        fromNameToAmount = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PaymentAccountNameScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit,
    fromNameToAmount: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(
        topBar = {
            PaymentAccountCreateTopAppBar(
                subTitle = R.string.payment_account_name_top_app_bar_subtitle,
                onCancelClick = onCancelClick
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = { fromNameToAmount() },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
                        enabled = null == paymentAccountCreateUi.nameError &&
                                paymentAccountCreateUi.name.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.next_step)) }
                    )
                }
            )
        },
        content = {

            val paymentAccountCardItem: PaymentAccountCardItem = remember(calculation = {
                getPaymentAccountCard(
                    paymentAccountCreateUi.type
                )
            })

            val originTypeDescription: String = remember(calculation = {
                paymentAccountCardItem.typeNameAccount
            })

            if (paymentAccountCreateUi.name.isNotBlank()) {
                paymentAccountCardItem.typeNameAccount = paymentAccountCreateUi.name
            } else {
                paymentAccountCardItem.typeNameAccount = originTypeDescription
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    PaymentAccountCard(
                        paymentAccountCardItem = paymentAccountCardItem,
                        onClickEnable = false
                    )
                    EnterPaymentAccountNameTextField(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
                    )
                }
            )
        }
    )
}

@Composable
fun EnterPaymentAccountNameTextField(
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit
) {
    OutlinedTextField(
        value = paymentAccountCreateUi.name,
        onValueChange = {
            if (it.length <= NameValidator.maxCharLimit) {
                onPaymentAccountCreateEventUi(
                    PaymentAccountUiEvent.NameChanged(it)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = 0 != paymentAccountCreateUi.type,
        label = { Text(text = stringResource(id = R.string.attribute_payment_account_name_field)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (paymentAccountCreateUi.nameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = paymentAccountCreateUi.nameError),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (paymentAccountCreateUi.nameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = paymentAccountCreateUi.nameError != null,
        singleLine = true
    )
}

