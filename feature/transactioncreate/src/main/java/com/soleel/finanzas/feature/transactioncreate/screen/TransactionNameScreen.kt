package com.soleel.finanzas.feature.transactioncreate.screen

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
import androidx.compose.runtime.MutableState
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
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.core.ui.template.CreateTopAppBar
import com.soleel.finanzas.core.ui.template.TransactionCard
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactioncreate.TransactionCreateViewModel
import com.soleel.finanzas.feature.transactioncreate.TransactionUiCreate
import com.soleel.finanzas.feature.transactioncreate.TransactionUiEvent
import java.util.Date


@Composable
internal fun TransactionNameRoute(
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
    viewModel: TransactionCreateViewModel
) {
    val transactionUiCreate: TransactionUiCreate = viewModel.transactionUiCreate

    TransactionNameScreen(
        modifier = Modifier,
        onAcceptCancel = onAcceptCancel,
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
        onAcceptCancel = {},
        onBackClick = {},
        transactionUiCreate = TransactionUiCreate(
            account = Account(
                id = "2",
                name = "Cuenta corriente falabella",
                amount = 400000,
                createAt = Date(),
                updatedAt = Date(),
                type = AccountTypeEnum.CREDIT
            ),
            transactionType = TransactionTypeEnum.EXPENDITURE.id,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_GIFT.id
        ),
        onTransactionCreateUiEvent = {},
        fromNameToAmount = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionNameScreen(
    modifier: Modifier,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    transactionUiCreate: TransactionUiCreate,
    onTransactionCreateUiEvent: (TransactionUiEvent) -> Unit,
    fromNameToAmount: () -> Unit
) {
    val showCancelAlert: MutableState<Boolean> = remember(calculation =  { mutableStateOf(false) })

    if (showCancelAlert.value) {
        CancelAlertDialog(
            onDismiss = { showCancelAlert.value = false },
            onConfirmation = {
                showCancelAlert.value = false
                onAcceptCancel()
            },
            dialogTitle = "¿Quieres volver atras?",
            dialogText = "Cancelaras la creacion de esta transaccion."
        )
    }

    BackHandler(enabled = true, onBack = onBackClick)

    Scaffold(
        topBar = {
            CreateTopAppBar(
                title= R.string.trasaction_create_title,
                subTitle = R.string.trasaction_name_top_app_bar_subtitle,
                onBackButton = { showCancelAlert.value = true }
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

            val AccountAmount: String = currencyVisualTransformation
                .filter(AnnotatedString(text = transactionUiCreate.account.amount.toString()))
                .text
                .toString()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    TransactionCard(
                        transactionUIValues = getTransactionUI(
                            accountTypeEnum = transactionUiCreate.account.type,
                            accountName = transactionUiCreate.account.name,
                            accountAmount = AccountAmount,
                            transactionType = TransactionTypeEnum.fromId(transactionUiCreate.transactionType),
                            transactionCategory = TransactionCategoryEnum.fromId(transactionUiCreate.transactionCategory),
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
