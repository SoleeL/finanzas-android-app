package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.domain.transformation.visualtransformation.CLPCurrencyVisualTransformation
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

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseDateSelectionScreen(
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
                    ExpenseDateSelectionScreen(
                        createExpenseViewModel = createExpenseViewModel,
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@Composable
fun ExpenseDateSelectionScreen(
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
                accountTypeEnum = createExpenseViewModel.createExpenseUiModel.accountType,
                account = createExpenseViewModel.createExpenseUiModel.account,
                instalments = createExpenseViewModel.createExpenseUiModel.instalments
            )

            Button(
                onClick = {
                    createExpenseViewModel.onCreateExpenseUiEvent(
                        CreateExpenseUiEvent.ExpenseDateSelected(LocalDateTime.now())
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