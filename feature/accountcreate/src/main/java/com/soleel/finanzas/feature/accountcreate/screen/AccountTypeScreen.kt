package com.soleel.finanzas.feature.accountcreate.screen


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AccountCard
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.core.ui.template.CreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.feature.accountcreate.AccountCreateViewModel
import com.soleel.finanzas.feature.accountcreate.AccountUiCreate
import com.soleel.finanzas.feature.accountcreate.AccountUiEvent
import com.soleel.finanzas.feature.accountcreate.util.AccountCards


@Composable
internal fun CreateSelectAccountTypeRoute(
    modifier: Modifier = Modifier,
    onAcceptCancel: () -> Unit,
    fromTypeToName: () -> Unit,
    viewModel: AccountCreateViewModel
) {
    val accountCreateUi: AccountUiCreate = viewModel.accountUiCreate

    CreateSelectAccountTypeScreen(
        modifier = modifier,
        onAcceptCancel = onAcceptCancel,
        fromTypeToName = fromTypeToName,
        accountCreateUi = accountCreateUi,
        onAccountCreateEventUi = viewModel::onAccountCreateEventUi
    )
}

@Preview
@Composable
fun CreateSelectAccountTypeScreenPreview() {
    CreateSelectAccountTypeScreen(
        modifier = Modifier,
        onAcceptCancel = {},
        fromTypeToName = {},
        accountCreateUi = AccountUiCreate(),
        onAccountCreateEventUi = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun CreateSelectAccountTypeScreen(
    modifier: Modifier,
    onAcceptCancel: () -> Unit,
    fromTypeToName: () -> Unit,
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit
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
            dialogText = "Cancelaras la creacion de esta cuenta."
        )
    }

    BackHandler(
        enabled = true,
        onBack = { showCancelAlert.value = false == showCancelAlert.value }
    )

    Scaffold(
        topBar = {
            CreateTopAppBar(
                title = R.string.account_create_title,
                subTitle = R.string.account_type_top_app_bar_subtitle,
                onBackButton = { showCancelAlert.value = true }
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
//                        enabled = 0 != AccountUiCreate.type,
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
                    SelectAccountType(
                        accountCreateUi = accountCreateUi,
                        onAccountCreateEventUi = onAccountCreateEventUi,
                        fromTypeToName = fromTypeToName
                    )
                }
            )
        }
    )
}

@Composable
fun SelectAccountType(
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit,
    fromTypeToName: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(
                items = AccountCards.cardsList,
                itemContent = { accountUIValues: AccountUIValues ->
                    AccountCard(
                        accountUIValues = accountUIValues,
                        onClick = {
                            onAccountCreateEventUi(
                                AccountUiEvent.TypeChanged(
                                    accountType = AccountTypeEnum.getIdByName(accountUIValues.type.name),
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