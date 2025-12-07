package com.soleel.finanzas.feature.createexpense.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.domain.account.GetAccountsWithExpensesInfoCurrentMonthUseCaseMock
import com.soleel.finanzas.feature.createexpense.CreateExpenseUiEvent
import com.soleel.finanzas.feature.createexpense.CreateExpenseViewModel
import com.soleel.finanzas.feature.createexpense.components.ExpenseSummaryHeader
import java.time.LocalDateTime
import java.util.UUID


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

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.AccountSelected(
            Account(
                id = UUID.randomUUID().toString(),
                type = AccountTypeEnum.DEBIT,
                name = "Banco falabella",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                synchronization = SynchronizationEnum.PENDING
            )
        )
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.ExpenseDateSelected(
            LocalDateTime.now()
        )
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseConfirmationScreen(
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

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.AccountSelected(
            Account(
                id = UUID.randomUUID().toString(),
                type = AccountTypeEnum.DEBIT,
                name = "CMR falabella",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                synchronization = SynchronizationEnum.PENDING
            )
        )
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.InstalmentsSelected(3)
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseConfirmationScreen(
                        createExpenseViewModel = createExpenseViewModel,
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@Composable
fun ExpenseConfirmationScreen(
    createExpenseViewModel: CreateExpenseViewModel,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            ExpenseSummaryHeader(
                amount = createExpenseViewModel.createExpenseUiModel.amount,
                itemCount = createExpenseViewModel.createExpenseUiModel.size,
                expenseTypeEnum = createExpenseViewModel.createExpenseUiModel.expenseType,
                account = createExpenseViewModel.createExpenseUiModel.account,
                instalments = createExpenseViewModel.createExpenseUiModel.instalments,
                date = createExpenseViewModel.createExpenseUiModel.expenseDate
            )

            Button(
                onClick = {
                    createExpenseViewModel.onCreateExpenseUiEvent(
                        CreateExpenseUiEvent.ExpenseName("Compra")
                    )
                    onContinue()
                },
                content = {
                    Text("Continuar")
                }
            )
        }
    )
}