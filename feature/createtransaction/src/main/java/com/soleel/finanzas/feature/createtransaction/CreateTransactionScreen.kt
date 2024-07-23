package com.soleel.finanzas.feature.createtransaction

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.core.ui.template.CreateTopAppBar
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.domain.validation.validator.ValidatorTransactionAmount
import java.util.Date


@Composable
internal fun CreateTransactionRoute(
    modifier: Modifier = Modifier,
    onBackToPreviousView: () -> Unit,
    viewModel: CreateTransactionViewModel = hiltViewModel()
) {
    val accountsUiState by viewModel.accountsUiState.collectAsStateWithLifecycle()

    val createTransactionUiState: CreateTransactionUiState = viewModel.createTransactionUiState

    TransactionCreateScreen(
        modifier = modifier,
        onBackToPreviousView = onBackToPreviousView,
        accountsUiState = accountsUiState,
        onAccountsUiEvent = viewModel::onAccountsUiEvent,
        createTransactionUiState = createTransactionUiState,
        onCreateTransactionUiEvent = viewModel::onCreateTransactionUiEvent
    )
}

@Preview
@Composable
private fun TransactionCreateScreenPreview() {
    TransactionCreateScreen(
        modifier = Modifier,
        onBackToPreviousView = {},
        accountsUiState = AccountsUiState.Success(
            accounts = listOf(
                Account(
                    id = "1",
                    name = "CMR Falabella",
                    amount = 240000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.CREDIT,
                ),
                Account(
                    id = "2",
                    name = "Falabella debito",
                    amount = 100000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.DEBIT,
                ),
                Account(
                    id = "3",
                    name = "Cuenta rut",
                    amount = 100000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.DEBIT,
                ),
                Account(
                    id = "4",
                    name = "Racional app",
                    amount = 9000000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.SAVING,
                )
            )
        ),
        onAccountsUiEvent = {},
        createTransactionUiState = CreateTransactionUiState(),
        onCreateTransactionUiEvent = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun TransactionCreateScreen(
    modifier: Modifier,
    onBackToPreviousView: () -> Unit,
    accountsUiState: AccountsUiState,
    onAccountsUiEvent: (AccountsUiEvent) -> Unit,
    createTransactionUiState: CreateTransactionUiState,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {

    val externalBackHandler: MutableState<Boolean> =
        remember(calculation = { mutableStateOf(true) })

    BackHandler(enabled = externalBackHandler.value, onBack = onBackToPreviousView)

    Scaffold(
        modifier = modifier,
        topBar = {
            CreateTopAppBar(
                title = R.string.trasaction_create_title,
                onBackButton = onBackToPreviousView
            )
        },
        bottomBar = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = { onCreateTransactionUiEvent(CreateTransactionUiEvent.Submit) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
                        enabled = createTransactionUiState.account.id.isNotBlank() &&
                                0 != createTransactionUiState.transactionType &&
                                0 != createTransactionUiState.transactionCategory &&
                                createTransactionUiState.transactionName.isNotBlank() &&
                                0 != createTransactionUiState.transactionAmount,
                        content = { Text(text = stringResource(id = R.string.save_transaction_button)) }
                    )
                }
            )
        },
        content = {
            when (accountsUiState) {
                is AccountsUiState.Success -> {
                    externalBackHandler.value = false
                    TransactionCreateSuccess(
                        modifier = Modifier.padding(it),
                        onBackToPreviousView = onBackToPreviousView,
                        accounts = accountsUiState.accounts,
                        createTransactionUiState = createTransactionUiState,
                        onCreateTransactionUiEvent = onCreateTransactionUiEvent
                    )
                }

                is AccountsUiState.Error -> TransactionCreateErrorScreen(
                    modifier = modifier,
                    onRetry = { onAccountsUiEvent(AccountsUiEvent.Retry) }
                )

                is AccountsUiState.Loading -> TransactionCreateLoadingScreen()
            }
        }
    )
}

@Composable
fun TransactionCreateSuccess(
    modifier: Modifier,
    onBackToPreviousView: () -> Unit,
    accounts: List<Account>,
    createTransactionUiState: CreateTransactionUiState,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {
    val showCancelAlert: MutableState<Boolean> = remember(calculation = { mutableStateOf(false) })

    if (showCancelAlert.value) {
        CancelAlertDialog(
            onDismiss = { showCancelAlert.value = false },
            onConfirmation = {
                showCancelAlert.value = false
                onBackToPreviousView()
            },
            dialogTitle = "¿Quieres volver atras?",
            dialogText = "Cancelaras la creacion de esta transaccion."
        )
    }

    BackHandler(enabled = true, onBack = { showCancelAlert.value = false == showCancelAlert.value })

    if (createTransactionUiState.isTransactionSaved) {
        onBackToPreviousView()
    }

    Column(
        modifier = modifier,
        content = {

            SelectAccountDropdownMenu(
                accounts = accounts,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )

            // TODO: Agregar la seleccion de tipo de transaccion
            // TODO: Agregar la seleccion de categoria de transaccion

            Spacer(modifier = Modifier.height(8.dp))

            InputTransactionNameTextField(
                createTransactionUiState = createTransactionUiState,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            InputTransactionAmountTextField(
                createTransactionUiState = createTransactionUiState,
                onCreateTransactionUiEvent = onCreateTransactionUiEvent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAccountDropdownMenu(
    accounts: List<Account>,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {
    var selectedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val accountsSummary: List<AccountsSummary> by remember(calculation = {
        mutableStateOf(
            value = accounts.map(transform = { account ->
                AccountsSummary(
                    name = account.name,
                    amount = "$0",
                    account = account
                )
            })
        )
    })

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = false == expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        content = {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type),
                        contentDescription = "Localized description"
                    )
                },
                value = selectedOption,
                onValueChange = {},
                label = { Text("Cuenta de pago") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    accountsSummary.forEach(
                        action = { account ->
                            val accountNameAmount: String = "${account.name} - ${account.amount}"
                            DropdownMenuItem(
                                text = { Text(text = accountNameAmount) },
                                onClick = {
                                    selectedOption = accountNameAmount
                                    expanded = false
                                    onCreateTransactionUiEvent(
                                        CreateTransactionUiEvent.AccountChanged(
                                            account.account
                                        )
                                    )
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun InputTransactionNameTextField(
    createTransactionUiState: CreateTransactionUiState,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit
) {
    OutlinedTextField(
        value = createTransactionUiState.transactionName,
        onValueChange = {
            onCreateTransactionUiEvent(
                CreateTransactionUiEvent.TransactionNameChanged(
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
        enabled = 0 != createTransactionUiState.transactionCategory,
        label = { Text(text = stringResource(id = R.string.attribute_trasaction_name_field)) },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if (createTransactionUiState.transactionNameError == null)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = createTransactionUiState.transactionNameError),
                textAlign = TextAlign.End,
            )
        },
        trailingIcon = {
            if (createTransactionUiState.transactionNameError != null) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    tint = Color.Red, // Cambiar color
                    contentDescription = "Nombre de la transaccion a crear"
                )
            }
        },
        isError = createTransactionUiState.transactionNameError != null,
        singleLine = true
    )
}

@Composable
fun InputTransactionAmountTextField(
    createTransactionUiState: CreateTransactionUiState,
    onCreateTransactionUiEvent: (CreateTransactionUiEvent) -> Unit,
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    OutlinedTextField(
        value = if (0 != createTransactionUiState.transactionAmount) createTransactionUiState.transactionAmount.toString() else "",
        onValueChange = { input: String ->
            val trimmed = input
                .trimStart('0')
                .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })

            if (trimmed.length <= ValidatorTransactionAmount.MAX_CHAR_LIMIT) {
                onCreateTransactionUiEvent(
                    CreateTransactionUiEvent.TransactionAmountChanged(
                        if (trimmed.isBlank()) 0 else trimmed.toInt()
                    )
                )
            }
        },
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        enabled = 0 != createTransactionUiState.transactionCategory,
        label = { Text(text = stringResource(id = R.string.attribute_trasaction_amount_field)) },
        trailingIcon = {
            if (null != createTransactionUiState.transactionAmountError) {
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
                text = if (null == createTransactionUiState.transactionAmountError)
                    stringResource(id = R.string.required_field) else
                    stringResource(id = createTransactionUiState.transactionAmountError),
                textAlign = TextAlign.End,
            )
        },
        isError = createTransactionUiState.transactionAmountError != null,
        visualTransformation = currencyVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Composable
fun TransactionCreateErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Error de carga",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hubo un problema al cargar los datos. Inténtalo de nuevo.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}

//@Preview
//@Composable
//fun TransactionCreateLoadingScreenPreview() {
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .background(color = MaterialTheme.colorScheme.background),
//        content = { TransactionCreateLoadingScreen() }
//    )
//}

@Composable
fun TransactionCreateLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CircularProgressIndicator(
                color = ProgressIndicatorDefaults.circularColor,
                strokeWidth = 5.dp,
                trackColor = ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "Obteniendo cuentas"
            )
        }
    )
}