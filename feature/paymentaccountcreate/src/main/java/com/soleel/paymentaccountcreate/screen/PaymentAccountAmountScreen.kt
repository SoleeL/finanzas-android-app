package com.soleel.paymentaccountcreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.common.constants.PaymentAccountTypeConstant
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.paymentaccountcreate.PaymentAccountUiCreate
import com.soleel.paymentaccountcreate.PaymentAccountUiEvent
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.ui.R
import com.soleel.ui.template.PaymentAccountCard
import com.soleel.ui.template.PaymentAccountCreateTopAppBar
import com.soleel.ui.util.PaymentAccountCardItem
import com.soleel.ui.util.getPaymentAccountCard
import com.soleel.validation.validator.TransactionAmountValidator


@Composable
internal fun PaymentAccountAmountRoute(
    modifier: Modifier = Modifier,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: PaymentAccountCreateViewModel
) {
    val paymentAccountCreateUi = viewModel.paymentAccountUiCreate

    PaymentAccountAmountScreen(
        modifier = modifier,

        showBottomBar = showBottomBar,
        showFloatingAddMenu = showFloatingAddMenu,
        hideExtendAddMenu = hideExtendAddMenu,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,
        onSaveClick = onSaveClick,

        paymentAccountCreateUi = paymentAccountCreateUi,
        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi
    )
}

@Preview
@Composable
fun PaymentAccountAmountScreenPreview() {
    PaymentAccountAmountScreen(
        modifier = Modifier,
        onBackClick = {},
        showBottomBar = {},
        showFloatingAddMenu = {},
        hideExtendAddMenu = {},
        onCancelClick = {},
        onSaveClick = {},
        paymentAccountCreateUi = PaymentAccountUiCreate(
            type = PaymentAccountTypeConstant.INVESTMENT,
            amount = "$340,000"
        ),
        onPaymentAccountCreateEventUi = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PaymentAccountAmountScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    if (paymentAccountCreateUi.isPaymentAccountSaved) {
        showBottomBar()
        showFloatingAddMenu()
        hideExtendAddMenu()
        onSaveClick()
    }

    Scaffold(
        topBar = {
            PaymentAccountCreateTopAppBar(
                subTitle = R.string.payment_account_amount_top_app_bar_subtitle,
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
                        onClick = { onPaymentAccountCreateEventUi(PaymentAccountUiEvent.Submit) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
                        enabled = 0 != paymentAccountCreateUi.type
                                && paymentAccountCreateUi.name.isNotBlank()
                                && paymentAccountCreateUi.amount.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.add_payment_account_button)) }
                    )
                }
            )
        },
        content = {

            val currencyVisualTransformation by remember(calculation = {
                mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
            })

            val paymentAccountCardItem: PaymentAccountCardItem = remember(calculation = {
                getPaymentAccountCard(
                    paymentAccountCreateUi.type
                )
            })

            paymentAccountCardItem.typeNameAccount = paymentAccountCreateUi.name

            val originAmount: String = remember(calculation = {
                paymentAccountCardItem.amount
            })

            if (paymentAccountCreateUi.amount.isNotBlank()) {
                paymentAccountCardItem.amount = currencyVisualTransformation
                    .filter(AnnotatedString(text = paymentAccountCreateUi.amount))
                    .text
                    .toString()
            } else {
                paymentAccountCardItem.amount = originAmount
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
                    EnterPaymentAccountAmountTextFlied(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi,
                        currencyVisualTransformation = currencyVisualTransformation
                    )
                }
            )
        }
    )
}

@Composable
fun EnterPaymentAccountAmountTextFlied(
    paymentAccountCreateUi: PaymentAccountUiCreate,
    onPaymentAccountCreateEventUi: (PaymentAccountUiEvent) -> Unit,
    currencyVisualTransformation: CurrencyVisualTransformation
) {
    OutlinedTextField(
        value = paymentAccountCreateUi.amount,
        onValueChange = { input ->
            val trimmed = input
                .trimStart('0')
                .trim(predicate = { it.isDigit().not() })

            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
                onPaymentAccountCreateEventUi(PaymentAccountUiEvent.AmountChanged(trimmed))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = 0 != paymentAccountCreateUi.type
                && paymentAccountCreateUi.name.isNotBlank(),
        label = { Text(text = stringResource(id = R.string.attribute_payment_account_amount_field)) },
        trailingIcon = {
            if (null != paymentAccountCreateUi.amountError) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Monto de la transaccion a crear"
                )
            }
        },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (paymentAccountCreateUi.amountError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = paymentAccountCreateUi.amountError),
                textAlign = TextAlign.End,
            )
        },
        isError = paymentAccountCreateUi.amountError != null,
        visualTransformation = currencyVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}