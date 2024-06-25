package com.soleel.finanzas.feature.accountcreate.screen

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
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AccountCard
import com.soleel.finanzas.core.ui.template.AccountCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.core.ui.uivalues.getAccountUI
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.domain.validation.validator.TransactionAmountValidator
import com.soleel.finanzas.feature.accountcreate.AccountCreateViewModel
import com.soleel.finanzas.feature.accountcreate.AccountUiCreate
import com.soleel.finanzas.feature.accountcreate.AccountUiEvent


@Composable
internal fun AccountAmountRoute(
    modifier: Modifier = Modifier,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: AccountCreateViewModel
) {
    val accountCreateUi = viewModel.accountUiCreate

    AccountAmountScreen(
        modifier = modifier,

        showTransactionsTab = showTransactionsTab,
        showBottomBar = showBottomBar,
        showFloatingAddMenu = showFloatingAddMenu,
        hideExtendAddMenu = hideExtendAddMenu,

        onBackClick = onBackClick,
        onCancelClick = onCancelClick,
        onSaveClick = onSaveClick,

        accountCreateUi = accountCreateUi,
        onAccountCreateEventUi = viewModel::onAccountCreateEventUi
    )
}

@Preview
@Composable
fun AccountAmountScreenPreview() {
    AccountAmountScreen(
        modifier = Modifier,
        onBackClick = {},
        showTransactionsTab = {},
        showBottomBar = {},
        showFloatingAddMenu = {},
        hideExtendAddMenu = {},
        onCancelClick = {},
        onSaveClick = {},
        accountCreateUi = AccountUiCreate(
            type = AccountTypeEnum.INVESTMENT.id,
            amount = "$340,000"
        ),
        onAccountCreateEventUi = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun AccountAmountScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onBackClick() }
    )

    if (accountCreateUi.isAccountSaved) {
        showTransactionsTab()
        showBottomBar()
        showFloatingAddMenu()
        hideExtendAddMenu()
        onSaveClick()
    }

    Scaffold(
        topBar = {
            AccountCreateTopAppBar(
                subTitle = R.string.account_amount_top_app_bar_subtitle,
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
                        onClick = { onAccountCreateEventUi(AccountUiEvent.Submit) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(64.dp),
                        enabled = 0 != accountCreateUi.type
                                && accountCreateUi.name.isNotBlank()
                                && accountCreateUi.amount.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.add_account_button)) }
                    )
                }
            )
        },
        content = {

            val currencyVisualTransformation by remember(calculation = {
                mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
            })

            val accountUIValues: AccountUIValues = remember(calculation = {
                getAccountUI(
                    accountTypeEnum = AccountTypeEnum.fromId(accountCreateUi.type),
                    accountName = accountCreateUi.name,
                    accountAmount = ""
                )
            })

            val originAmount: String = remember(calculation = {
                accountUIValues.amount
            })

            if (accountCreateUi.amount.isNotBlank()) {
                accountUIValues.amount = currencyVisualTransformation
                    .filter(AnnotatedString(text = accountCreateUi.amount))
                    .text
                    .toString()
            } else {
                accountUIValues.amount = originAmount
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                content = {
                    AccountCard(
                        accountUIValues = accountUIValues,
                        onClickEnable = false
                    )
                    EnterAccountAmountTextFlied(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi,
                        currencyVisualTransformation = currencyVisualTransformation
                    )
                }
            )
        }
    )
}

@Composable
fun EnterAccountAmountTextFlied(
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit,
    currencyVisualTransformation: CurrencyVisualTransformation
) {
    OutlinedTextField(
        value = accountCreateUi.amount,
        onValueChange = { input ->
            val trimmed = input
                .trimStart('0')
                .trim(predicate = { it.isDigit().not() })

            if (trimmed.length <= TransactionAmountValidator.maxCharLimit) {
                onAccountCreateEventUi(AccountUiEvent.AmountChanged(trimmed))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = 0 != accountCreateUi.type
                && accountCreateUi.name.isNotBlank(),
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