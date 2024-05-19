package com.soleel.transactioncreate.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
import com.soleel.finanzas.core.common.constants.TransactionCategoryConstant
import com.soleel.finanzas.core.common.constants.TransactionTypeConstant
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.transactioncreate.TransactionCreateViewModel
import com.soleel.transactioncreate.TransactionUiCreate
import com.soleel.transactioncreate.TransactionUiEvent
import com.soleel.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.TransactionCard
import com.soleel.finanzas.core.ui.template.TransactionCreateTopAppBar
import com.soleel.finanzas.core.ui.util.getPaymentAccountCard
import com.soleel.finanzas.core.ui.util.getTransactionCategoryCard
import com.soleel.finanzas.core.ui.util.getTransactionTypeCard


@Composable
internal fun TransactionNameRoute(
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionNameScreen(
        modifier = Modifier,
        onCancelClick = onCancelClick,
        onBackClick = onBackClick,
        transactionUiCreate = transactionUiCreate,
        onTransactionCreateUiEvent = viewModel::onTransactionCreateUiEvent,
        fromNameToAmount = fromNameToAmount
    )
}

@Preview
@Composable
fun TransactionNameScreenPreview() {
    TransactionNameScreen(
        modifier = Modifier,
        onCancelClick = {},
        onBackClick = {},
        transactionUiCreate = TransactionUiCreate(
            paymentAccount = PaymentAccount(
                id = "2",
                name = "Cuenta corriente falabella",
                amount = 400000,
                createAt = 1708709787983L,
                updatedAt = 1708709787983L,
                accountType = PaymentAccountTypeConstant.CREDIT
            ),
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_GIFT
        ),
        onTransactionCreateUiEvent = {},
        fromNameToAmount = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionNameScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromNameToAmount: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    Scaffold(
        topBar = {
            TransactionCreateTopAppBar(
                subTitle = R.string.trasaction_name_top_app_bar_subtitle,
                onClick = onCancelClick
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
                        enabled = transactionUiCreate.transactionName.isNotBlank() &&
                                null == transactionUiCreate.transactionNameError,
                        content = { Text(text = "Avanzar a ingresar monto") }
                    )
                }
            )
        },
        content = {

            val currencyVisualTransformation by remember(calculation = {
                mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
            })

            val paymentAccountAmount: String = currencyVisualTransformation
                .filter(AnnotatedString(text = transactionUiCreate.paymentAccount.amount.toString()))
                .text
                .toString()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    TransactionCard(
                        transactionTypeCardItem = getTransactionTypeCard(
                            transactionType = transactionUiCreate.transactionType
                        ),
                        paymentAccountCardItem = getPaymentAccountCard(
                            paymentAccountType = transactionUiCreate.paymentAccount.accountType,
                            paymentAccountTypeName = transactionUiCreate.paymentAccount.name,
                            amount = paymentAccountAmount
                        ),
                        transactionCategoryCardItem = getTransactionCategoryCard(
                            transactionType = transactionUiCreate.transactionType,
                            transactionCategory = transactionUiCreate.transactionCategory,
                            transactionName = transactionUiCreate.transactionName,
                            transactionAmount = ""
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    EnterTransactionNameTextField(
                        transactionUiCreate = transactionUiCreate,
                        onTransactionCreateUiEvent = onTransactionCreateUiEvent
                    )
                }
            )
        }
    )

}

@Composable
fun EnterTransactionNameTextField(
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit
) {
    OutlinedTextField(
        value = transactionUiCreate.transactionName,
        onValueChange = {
            onTransactionCreateUiEvent(
                TransactionUiEvent.TransactionNameChanged(
                    it
                )
            )
        },
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        enabled = 0 != transactionUiCreate.transactionCategory,
        label = { Text(text = stringResource(id = R.string.attribute_trasaction_name_field)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (transactionUiCreate.transactionNameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = transactionUiCreate.transactionNameError),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (transactionUiCreate.transactionNameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = transactionUiCreate.transactionNameError != null,
        singleLine = true
    )
}
