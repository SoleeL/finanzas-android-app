package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.domain.transformation.visualtransformation.CLPCurrencyVisualTransformation


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

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    AccountTypeSelectionScreen(
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

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    AccountTypeSelectionScreen(
                        createExpenseViewModel = createExpenseViewModel,
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@Composable
fun AccountTypeSelectionScreen(
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
                expenseTypeEnum = createExpenseViewModel.createExpenseUiModel.expenseType
            )

            Button(
                onClick = {
                    createExpenseViewModel.onCreateExpenseUiEvent(
                        CreateExpenseUiEvent.AccountTypeSelected(AccountTypeEnum.DEBIT)
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
