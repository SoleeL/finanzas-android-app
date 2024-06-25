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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AccountCard
import com.soleel.finanzas.core.ui.template.AccountCreateTopAppBar
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.feature.accountcreate.AccountCreateViewModel
import com.soleel.finanzas.feature.accountcreate.AccountUiCreate
import com.soleel.finanzas.feature.accountcreate.AccountUiEvent
import com.soleel.finanzas.feature.accountcreate.util.AccountCards


@Composable
internal fun CreateSelectAccountTypeRoute(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit,
    viewModel: AccountCreateViewModel
) {
    val accountCreateUi: AccountUiCreate = viewModel.accountUiCreate

    CreateSelectAccountTypeScreen(
        modifier = modifier,
        onCancelClick = onCancelClick,
        accountCreateUi = accountCreateUi,
        onAccountCreateEventUi = viewModel::onAccountCreateEventUi,
        fromTypeToName = fromTypeToName
    )
}

@Preview
@Composable
fun CreateSelectAccountTypeScreenPreview() {
    CreateSelectAccountTypeScreen(
        modifier = Modifier,
        onCancelClick = {},
        accountCreateUi = AccountUiCreate(),
        onAccountCreateEventUi = {},
        fromTypeToName = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun CreateSelectAccountTypeScreen(
    modifier: Modifier,
    onCancelClick: () -> Unit,
    accountCreateUi: AccountUiCreate,
    onAccountCreateEventUi: (AccountUiEvent) -> Unit,
    fromTypeToName: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = { onCancelClick() }
    )

    Scaffold(
        topBar = {
            AccountCreateTopAppBar(
                subTitle = R.string.account_type_top_app_bar_subtitle,
                onCancelClick = onCancelClick
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