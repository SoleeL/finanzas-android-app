package com.soleel.paymentaccountcreate

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.paymentaccountcreate.screen.SelectTypeAccountDropdownMenu
import com.soleel.ui.R
import com.soleel.ui.state.PaymentAccountCreateEventUi
import com.soleel.ui.state.PaymentAccountCreateUi
import com.soleel.ui.template.PaymentAccountCreateTopAppBar


@Composable
internal fun PaymentAccountCreateRoute(
    modifier: Modifier = Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    viewModel: PaymentAccountCreateViewModel = hiltViewModel()
) {
    val paymentAccountCreateUi = viewModel.paymentAccountCreateUi

    CreatePaymentAccountScreen(
        modifier = modifier,

        onShowBottomBar = onShowBottomBar,
        onShowAddFloating = onShowAddFloating,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,

        paymentAccountCreateUi = paymentAccountCreateUi,

        onPaymentAccountCreateEventUi = viewModel::onPaymentAccountCreateEventUi
    )
}

@Composable
internal fun CreatePaymentAccountScreen(
    modifier: Modifier,

    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,

    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,

    paymentAccountCreateUi: PaymentAccountCreateUi,

    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {

    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    if (paymentAccountCreateUi.isPaymentAccountSaved) {
        onShowBottomBar()
        onShowAddFloating()
        onBackClick()
    }

    Scaffold(
        topBar = {
            PaymentAccountCreateTopAppBar(
                subTitle = R.string.create_payment_account_title,
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
                        onClick = { onPaymentAccountCreateEventUi(PaymentAccountCreateEventUi.Submit) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
                        enabled = 0 != paymentAccountCreateUi.type
                                && paymentAccountCreateUi.name.isNotBlank()
                                && paymentAccountCreateUi.amount.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.add_payment_account_title)) }
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                content = {
                    PaymentAccountCreateForm(
                        paymentAccountCreateUi = paymentAccountCreateUi,
                        onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
                    )
                }
            )
        }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CreatePaymentAccountCenterAlignedTopAppBar(
//    subTitle: Int,
//    onCancelClick: () -> Unit
//) {
//    CenterAlignedTopAppBar(
//        title = {
//            Text(
//                text = stringResource(id = R.string.create_payment_account_title),
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleMedium
//            )
//            Text(
//                text = stringResource(id = subTitle),
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleSmall
//            )
//        },
//        navigationIcon = {
//            IconButton(
//                onClick = { onCancelClick() },
//                content = {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "Volver a la pantalla principal",
//                    )
//                }
//            )
//        }
//    )
//}

//@Preview
//@Composable
//fun PaymentAccountCard(
//    viewModel: PaymentAccountCreateViewModel
//) {
//    val paymentAccount by viewModel.paymentAccountUiCreate
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//        border = BorderStroke(width = 6.dp, color = Color.Black),
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                content = {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically,
//                        content = {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                content = {
//                                    Icon(
//                                        imageVector = Icons.Filled.AccountBox,
//                                        contentDescription = "Payment Account Icon",
//                                        tint = Color.Black
//                                    )
//                                    Text(
//                                        text = paymentAccount.type.toString(),
//                                        style = MaterialTheme.typography.titleMedium
//                                    )
//                                }
//                            )
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                content = {
//                                    Text(
//                                        text = "$3.000.000",
//                                        style = MaterialTheme.typography.titleLarge
//                                    )
//                                    Text(
//                                        text = "$1.500.000 ocupado",
//                                        style = MaterialTheme.typography.titleSmall,
//                                    )
//                                }
//                            )
//                        }
//                    )
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 96.dp),
//                        content = {
//                            Text(
//                                text = "1234 5678 9012 3456",
//                                style = MaterialTheme.typography.displaySmall,
//                                textAlign = TextAlign.Center
//                            )
//                            Text(
//                                text = "Nombre de la cuenta",
//                                style = MaterialTheme.typography.titleLarge,
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    )
//                }
//            )
//        }
//    )
//}

@Composable
fun PaymentAccountCreateForm(
    paymentAccountCreateUi: PaymentAccountCreateUi,
    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        content = {
            SelectTypeAccountDropdownMenu(
                paymentAccountCreateUi = paymentAccountCreateUi,
                onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
            )

            Spacer(modifier = Modifier.height(8.dp))

//            EnterTransactionNameTextField(
//                paymentAccountCreateUi = paymentAccountCreateUi,
//                onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
//            )

            Spacer(modifier = Modifier.height(8.dp))

//            EnterTransactionAmountTextFlied(
//                paymentAccountCreateUi = paymentAccountCreateUi,
//                onPaymentAccountCreateEventUi = onPaymentAccountCreateEventUi
//            )
        }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SelectTypeAccountDropdownMenu(
//    paymentAccountCreateUi: PaymentAccountCreateUi,
//    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
//) {
//    val accountTypes: List<Pair<Int, String>> = PaymentAccountTypeConstant.idToValueList
//
//    var selectedOption by remember { mutableStateOf("") }
//    var expanded by remember { mutableStateOf(false) }
//
//    ExposedDropdownMenuBox(
//        modifier = Modifier.fillMaxWidth(),
//        expanded = expanded,
//        onExpandedChange = { expanded = false == expanded },
//        content = {
//            OutlinedTextField(
//                value = selectedOption,
//                onValueChange = {},
//                modifier = Modifier
//                    .menuAnchor()
//                    .fillMaxWidth(),
//                readOnly = true,
//                label = { Text(text = stringResource(id = R.string.attribute_type_payment_account_title)) },
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                supportingText = {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = if (paymentAccountCreateUi.typeError == null)
//                            stringResource(id = R.string.required_field) else
//                            stringResource(id = paymentAccountCreateUi.typeError!!),
//                        textAlign = TextAlign.End,
//                    )
//                },
//                isError = paymentAccountCreateUi.typeError != null,
//            )
//            ExposedDropdownMenu(
//                modifier = Modifier
//                    .menuAnchor()
//                    .fillMaxWidth(),
//                expanded = expanded,
//                onDismissRequest = { expanded = false },
//                content = {
//                    accountTypes.forEach(
//                        action = { type ->
//                            DropdownMenuItem(
//                                text = { Text(text = type.second) },
//                                onClick = {
//
//                                    selectedOption = type.second
//                                    expanded = false
//                                    onPaymentAccountCreateEventUi(
//                                        PaymentAccountCreateEventUi.AccountTypeChangedUi(
//                                            type = type.first
//                                        )
//                                    )
//                                },
//                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
//                            )
//                        }
//                    )
//                }
//            )
//        }
//    )
//}

//@Composable
//fun EnterTransactionNameTextField(
//    paymentAccountCreateUi: PaymentAccountCreateUi,
//    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
//) {
//    OutlinedTextField(
//        value = paymentAccountCreateUi.name,
//        onValueChange = {
//            onPaymentAccountCreateEventUi(
//                PaymentAccountCreateEventUi.NameChanged(it)
//            )
//        },
//        modifier = Modifier.fillMaxWidth(),
//        enabled = 0 != paymentAccountCreateUi.type,
//        label = { Text(text = stringResource(id = R.string.attribute_name_payment_account_title)) },
//        supportingText = {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = if (paymentAccountCreateUi.nameError == null)
//                    stringResource(id = R.string.required_field) else
//                    stringResource(id = paymentAccountCreateUi.nameError!!),
//                textAlign = TextAlign.End,
//            )
//        },
//        trailingIcon = {
//            if (paymentAccountCreateUi.nameError != null) {
//                Icon(
//                    imageVector = Icons.Filled.Info,
//                    tint = Color.Red, // Cambiar color
//                    contentDescription = "Nombre de la transaccion a crear"
//                )
//            }
//        },
//        isError = paymentAccountCreateUi.nameError != null,
//        singleLine = true
//    )
//}

//@Composable
//fun EnterTransactionAmountTextFlied(
//    paymentAccountCreateUi: PaymentAccountCreateUi,
//    onPaymentAccountCreateEventUi: (PaymentAccountCreateEventUi) -> Unit
//) {
//
//    val currencyVisualTransformation by remember(calculation = {
//        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
//    })
//
//    OutlinedTextField(
//        value = paymentAccountCreateUi.amount,
//        onValueChange = {
//            val trimmed = it
//                .trimStart('0')
//                .trim(predicate = { inputTrimStart -> inputTrimStart.isDigit().not() })
//
//            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
//                onPaymentAccountCreateEventUi(PaymentAccountCreateEventUi.AmountChanged(trimmed))
//            }
//        },
//        modifier = Modifier.fillMaxWidth(),
//        enabled = 0 != paymentAccountCreateUi.type,
//        label = { Text(text = stringResource(id = R.string.attribute_amount_payment_account_title)) },
//        trailingIcon = {
//            if (paymentAccountCreateUi.amountError != null) {
//                Icon(
//                    imageVector = Icons.Filled.Info,
//                    tint = Color.Red, // Cambiar color
//                    contentDescription = "Monto de la transaccion a crear"
//                )
//            }
//        },
//        supportingText = {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = if (paymentAccountCreateUi.amountError == null)
//                    stringResource(id = R.string.required_field) else
//                    stringResource(id = paymentAccountCreateUi.amountError!!),
//                textAlign = TextAlign.End,
//            )
//        },
//        isError = paymentAccountCreateUi.amountError != null,
//        visualTransformation = currencyVisualTransformation,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        singleLine = true
//    )
//}