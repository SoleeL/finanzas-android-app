package com.soleel.finanzas.feature.createexpense.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.formatter.FullReadableDailyDateFormat
import com.soleel.finanzas.core.formatter.FullReadableDateFormat
import com.soleel.finanzas.core.model.AccountWithExpensesInfo
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.domain.account.GetAccountsWithExpensesInfoCurrentMonthUseCaseMock
import com.soleel.finanzas.feature.createexpense.AccountsUiEvent
import com.soleel.finanzas.feature.createexpense.AccountsUiState
import com.soleel.finanzas.feature.createexpense.CreateExpenseUiEvent
import com.soleel.finanzas.feature.createexpense.CreateExpenseViewModel
import com.soleel.finanzas.feature.createexpense.components.ExpenseSummaryHeader

@LongDevicePreview
@Composable
private fun CalculatorScreenLongPreview() {
    val mockItems: List<Item> = listOf(
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
    )

    val fakeSavedStateHandle = SavedStateHandle(
        mapOf("items" to mockItems)
    )

    val createExpenseViewModel: CreateExpenseViewModel = CreateExpenseViewModel(
        savedStateHandle = fakeSavedStateHandle,
        getAccountsUseCase = GetAccountsWithExpensesInfoCurrentMonthUseCaseMock(),
        retryableFlowTrigger = RetryableFlowTrigger()
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.ExpenseTypeSelected(ExpenseTypeEnum.OTHER)
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    AccountSelectionScreen(
                        createExpenseViewModel = createExpenseViewModel,
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@ShortDevicePreview
@Composable
private fun CalculatorScreenShortPreview() {
    val mockItems: List<Item> = listOf(
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
    )

    val fakeSavedStateHandle = SavedStateHandle(
        mapOf("items" to mockItems)
    )

    val createExpenseViewModel: CreateExpenseViewModel = CreateExpenseViewModel(
        savedStateHandle = fakeSavedStateHandle,
        getAccountsUseCase = GetAccountsWithExpensesInfoCurrentMonthUseCaseMock(),
        retryableFlowTrigger = RetryableFlowTrigger()
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.ExpenseTypeSelected(ExpenseTypeEnum.MARKET)
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    AccountSelectionScreen(
                        createExpenseViewModel = createExpenseViewModel,
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@Composable
fun AccountSelectionScreen(
    createExpenseViewModel: CreateExpenseViewModel,
    onContinue: () -> Unit,
) {
    val accountsUiState: AccountsUiState by createExpenseViewModel.accountsUiStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            ExpenseSummaryHeader(
                amount = createExpenseViewModel.createExpenseUiModel.amount,
                itemCount = createExpenseViewModel.createExpenseUiModel.size,
                expenseTypeEnum = createExpenseViewModel.createExpenseUiModel.expenseType
            )

            AccountsList(
                accountsUiState = accountsUiState,
                accountSelection = { account ->
                    createExpenseViewModel.onCreateExpenseUiEvent(
                        CreateExpenseUiEvent.AccountSelected(account)
                    )
                    onContinue()
                },
                onRetry = { createExpenseViewModel.onAccountsUiEvent(AccountsUiEvent.Retry) }

            )
        }
    )
}

@Composable
fun AccountsList(
    accountsUiState: AccountsUiState,
    accountSelection: (account: Account) -> Unit,
    onRetry: () -> Unit,
) {
    when (accountsUiState) {
        is AccountsUiState.Success -> AccountsSuccessScreen(
            accountsWithInfo = accountsUiState.accountsWithInfo,
            accountSelection = accountSelection
        )

        is AccountsUiState.Error -> AccountsErrorScreen(onRetry = onRetry)
        is AccountsUiState.Loading -> AccountsLoadingScreen()
    }
}

@Composable
fun AccountsSuccessScreen(
    accountsWithInfo: List<AccountWithExpensesInfo>,
    accountSelection: (account: Account) -> Unit,
//    singleEventManager: SingleEventManager
) {
    if (accountsWithInfo.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            content = {
                Text(
                    text = "No existen cuentas actualmente.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(8.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(
                    items = accountsWithInfo,
                    itemContent = { accountWithInfo ->
                        Button(
                            onClick = { accountSelection(accountWithInfo.account) },
                            shape = RoundedCornerShape(25),
                            content = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            content = {
                                                Icon(
                                                    painter = painterResource(id = accountWithInfo.account.type.icon),
                                                    contentDescription = accountWithInfo.account.type.value,
                                                    modifier = Modifier.size(48.dp)
                                                )
                                                Text(
                                                    text = accountWithInfo.account.type.value,
                                                    style = MaterialTheme.typography.titleSmall,
                                                )
                                            }
                                        )
                                        Column(
                                            horizontalAlignment = Alignment.End,
                                            content = {
                                                Text(
                                                    text = accountWithInfo.account.name,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    maxLines = 1,

                                                )
                                                Text(
                                                    text = "ultimo gasto el ${accountWithInfo.lastExpenseDate?.toLocalDate()
                                                        ?.let { FullReadableDateFormat(it) }}",
                                                    style = MaterialTheme.typography.titleMedium,
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun AccountsErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
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

@Composable
fun AccountsLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            CircularProgressIndicator(
                color = ProgressIndicatorDefaults.circularColor,
                strokeWidth = 5.dp,
                trackColor = ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )
        }
    )
}