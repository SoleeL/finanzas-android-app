package com.soleel.finanzas.feature.accountcreate.screen

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AccountCard
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.core.ui.template.CreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.core.ui.uivalues.getAccountUI
import com.soleel.finanzas.domain.validation.validator.NameValidator
import com.soleel.finanzas.feature.accountcreate.AccountCreateViewModel
import com.soleel.finanzas.feature.accountcreate.AccountUiCreate
import com.soleel.finanzas.feature.accountcreate.AccountUiEvent


@Composable
internal fun AccountNameRoute(
    modifier: Modifier = Modifier,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit,
    viewModel: AccountCreateViewModel
) {
    val accountCreateUi = viewModel.accountUiCreate

    AccountNameScreen(
        modifier = modifier,
        onAcceptCancel = onAcceptCancel,
        onBackClick = onBackClick,
        fromNameToAmount = fromNameToAmount,
        accountCreateUi = accountCreateUi,
        onAccountCreateEventUi = viewModel::onAccountCreateEventUi,
    )
}

@Preview
@Composable
internal fun AccountNameScreenPreview() {
    AccountNameScreen(
        modifier = Modifier,
        onAcceptCancel = {},
        onBackClick = {},
        fromNameToAmount = {},
        accountCreateUi = AccountUiCreate(
            type = AccountTypeEnum.CREDIT.id,
            name = "Inversion en bolsa",
        ),
        onAccountCreateEventUi = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun AccountNameScreen(
    modifier: Modifier,
    onAcceptCancel: () -> Unit,
    onBackClick: () -> Unit,
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit,
    fromNameToAmount: () -> Unit
) {
    val showCancelAlert: MutableState<Boolean> = remember(calculation = { mutableStateOf(false) })

    if (showCancelAlert.value) {
        CancelAlertDialog(
            onDismiss = { showCancelAlert.value = false },
            onConfirmation = {
                showCancelAlert.value = false
                onAcceptCancel()
            },
            dialogTitle = "¿Quieres volver atras?",
            dialogText = "Cancelaras la creacion de esta cuenta."
        )
    }

    BackHandler(enabled = true, onBack = onBackClick)

    Scaffold(
        topBar = {
            CreateTopAppBar(
                title = R.string.account_create_title,
                subTitle = R.string.account_name_top_app_bar_subtitle,
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
                        enabled = null == accountCreateUi.nameError &&
                                accountCreateUi.name.isNotBlank(),
                        content = { Text(text = stringResource(id = R.string.next_step)) }
                    )
                }
            )
        },
        content = {
            val accountUIValues: AccountUIValues = remember(calculation = {
                getAccountUI(
                    accountTypeEnum = AccountTypeEnum.fromId(accountCreateUi.type),
                    accountName = "",
                    accountAmount = ""
                )
            })

            val originTypeDescription: String = remember(calculation = {
                accountUIValues.name
            })

            if (accountCreateUi.name.isNotBlank()) {
                accountUIValues.name = accountCreateUi.name
            } else {
                accountUIValues.name = originTypeDescription
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
                    EnterAccountNameTextField(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi
                    )
                }
            )
        }
    )
}

@Composable
fun EnterAccountNameTextField(
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit
) {
    OutlinedTextField(
        value = accountCreateUi.name,
        onValueChange = {
            if (it.length <= NameValidator.maxCharLimit) {
                onAccountCreateEventUi(
                    AccountUiEvent.NameChanged(it)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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

