package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
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
        savedStateHandle = fakeSavedStateHandle
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.ExpenseTypeSelected(ExpenseTypeEnum.OTHER)
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.AccountTypeSelected(AccountTypeEnum.DEBIT)
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
        savedStateHandle = fakeSavedStateHandle
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.ExpenseTypeSelected(ExpenseTypeEnum.MARKET)
    )

    createExpenseViewModel.onCreateExpenseUiEvent(
        CreateExpenseUiEvent.AccountTypeSelected(AccountTypeEnum.CREDIT)
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            ExpenseSummaryHeader(
                amount = createExpenseViewModel.createExpenseUiModel.amount,
                itemCount = createExpenseViewModel.createExpenseUiModel.size,
                expenseTypeEnum = createExpenseViewModel.createExpenseUiModel.expenseType,
                accountTypeEnum = createExpenseViewModel.createExpenseUiModel.accountType
            )

            LazyColumn(
                contentPadding = PaddingValues(8.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(
                        items = AccountTypeEnum.entries,
                        itemContent = { accountTypeEnum ->
                            Button(
                                onClick = {
                                    createExpenseViewModel.onCreateExpenseUiEvent(
                                        CreateExpenseUiEvent.AccountSelected(
                                            Account(
                                                id = UUID.randomUUID().toString(),
                                                type = AccountTypeEnum.CREDIT,
                                                name = "CMR falabella",
                                                createdAt = LocalDateTime.now(),
                                                updatedAt = LocalDateTime.now(),
                                                synchronization = SynchronizationEnum.PENDING
                                            )
                                        )
                                    )
                                    onContinue()
                                },
                                shape = RoundedCornerShape(25),
                                content = {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = accountTypeEnum.icon),
                                                contentDescription = accountTypeEnum.value,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                content = {
                                                    Text(
                                                        text = accountTypeEnum.value,
                                                        style = MaterialTheme.typography.headlineLarge,
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
    )
}