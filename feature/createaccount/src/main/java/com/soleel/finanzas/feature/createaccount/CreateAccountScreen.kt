package com.soleel.finanzas.feature.createaccount

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.component.CancelAlertDialog
import com.soleel.finanzas.core.component.CreateTopAppBar
import com.soleel.finanzas.core.component.LargeDropdownMenu
import com.soleel.finanzas.core.component.onSingleClick
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountAmount
import com.soleel.finanzas.domain.validation.validator.ValidatorName


@Composable
internal fun CreateAccountRoute(
    modifier: Modifier = Modifier,
    onBackToPreviousView: () -> Unit,
    viewModel: CreateAccountViewModel = hiltViewModel()
) {
    val accountCreateUi = viewModel.createAccountUi

    val singleEventManager = viewModel.singleEventManager

    CreateAccountScreen(
        modifier = modifier,
        onBackToPreviousView = onBackToPreviousView,
        accountCreateUi = accountCreateUi,
        onAccountCreateEventUi = viewModel::onCreateAccountEventUi,
        isValidSaveAccount = viewModel::isValidSaveTransaction,
        singleEventManager = singleEventManager
    )
}

@Preview
@Composable
fun AccountAmountScreenPreview() {
    CreateAccountScreen(
        modifier = Modifier,
        onBackToPreviousView = {},
        accountCreateUi = CreateAccountUi(
            type = AccountTypeEnum.INVESTMENT.id,
            name = "Cuenta corriente",
            amount = 340000
        ),
        onAccountCreateEventUi = {},
        isValidSaveAccount = { true },
        singleEventManager = SingleEventManager()
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun CreateAccountScreen(
    modifier: Modifier,
    onBackToPreviousView: () -> Unit,
    accountCreateUi: CreateAccountUi,
    onAccountCreateEventUi: (CreateAccountEventUi) -> Unit,
    isValidSaveAccount: () -> Boolean,
    singleEventManager: SingleEventManager
) {
    val showCancelAlert: MutableState<Boolean> = remember(calculation = { mutableStateOf(false) })

    if (showCancelAlert.value) {
        com.soleel.finanzas.core.component.CancelAlertDialog(
            onDismiss = { showCancelAlert.value = false },
            onConfirmation = {
                showCancelAlert.value = false
                onBackToPreviousView()
            },
            dialogTitle = "¿Quieres volver atras?",
            dialogText = "Cancelaras la creacion de esta cuenta."
        )
    }

    BackHandler(enabled = true, onBack = { showCancelAlert.value = false == showCancelAlert.value })

    if (accountCreateUi.isAccountSaved) {
        onBackToPreviousView()
    }

    Scaffold(
        topBar = {
            com.soleel.finanzas.core.component.CreateTopAppBar(
                title = R.string.account_create_title,
                singleEventManager = singleEventManager,
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
                        onClick = { onAccountCreateEventUi(CreateAccountEventUi.Submit) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp)
                            .onSingleClick(
                                singleEventManager = singleEventManager,
                                onClick = { }
                            ),
                        enabled = isValidSaveAccount(),
                        content = { Text(text = stringResource(id = R.string.save_account_button)) }
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    SelectTypeAccountDropdownMenu(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi,
                        singleEventManager = singleEventManager
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    EnterAccountNameTextField(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi,
                    )

                    EnterAccountAmountTextField(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi
                    )
                }
            )
        }
    )
}

@Composable
fun SelectTypeAccountDropdownMenu(
    accountCreateUi: CreateAccountUi,
    onAccountCreateEventUi: (CreateAccountEventUi) -> Unit,
    singleEventManager: SingleEventManager
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        content = {
            com.soleel.finanzas.core.component.LargeDropdownMenu(
                singleEventManager = singleEventManager,
                label = "Tipo de cuenta",
                items = AccountTypeEnum.entries,
                selectedIndex = selectedIndex,
                onItemSelected = { index, accountType ->
                    selectedIndex = index
                    onAccountCreateEventUi(
                        CreateAccountEventUi.TypeChanged(
                            accountType.id
                        )
                    )
                },
                selectedItemToStartString = { accountType: AccountTypeEnum ->
                    accountType.value
                }
            )
        }
    )
}

@Composable
fun EnterAccountNameTextField(
    accountCreateUi: CreateAccountUi,
    onAccountCreateEventUi: (CreateAccountEventUi) -> Unit
) {
    OutlinedTextField(
        value = accountCreateUi.name,
        onValueChange = {
            if (it.length <= ValidatorName.MAX_CHAR_LIMIT) {
                onAccountCreateEventUi(
                    CreateAccountEventUi.NameChanged(it)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        enabled = 0 != accountCreateUi.type,
        label = { Text(text = stringResource(id = R.string.attribute_account_name_field)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (accountCreateUi.nameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = accountCreateUi.nameError),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (accountCreateUi.nameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = accountCreateUi.nameError != null,
        singleLine = true
    )
}

@Composable
fun EnterAccountAmountTextField(
    accountCreateUi: CreateAccountUi,
    onAccountCreateEventUi: (CreateAccountEventUi) -> Unit
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    OutlinedTextField(
        value = if (0 != accountCreateUi.amount) accountCreateUi.amount.toString() else "",
        onValueChange = { input: String ->
            val trimmed = input
                .trimStart('0')
                .trim(predicate = { it.isDigit().not() })

            if (trimmed.length <= ValidatorAccountAmount.MAX_AMOUNT_LIMIT) {
                onAccountCreateEventUi(
                    CreateAccountEventUi.AmountChanged(
                        if (trimmed.isBlank()) 0
                        else trimmed.toInt()
                    )
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        enabled = 0 != accountCreateUi.type && accountCreateUi.name.isNotBlank(),
        label = { Text(text = stringResource(id = R.string.attribute_account_amount_field)) },
        trailingIcon = {
            if (null != accountCreateUi.amountError) {
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
                text = if (accountCreateUi.amountError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = accountCreateUi.amountError),
                textAlign = TextAlign.End,
            )
        },
        isError = accountCreateUi.amountError != null,
        visualTransformation = currencyVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}
