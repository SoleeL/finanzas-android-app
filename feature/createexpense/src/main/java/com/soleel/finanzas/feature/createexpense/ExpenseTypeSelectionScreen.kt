package com.soleel.finanzas.feature.createexpense

//import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar


@LongDevicePreview
@Composable
private fun CalculatorScreenLongPreview() {
    val mockItems: List<Item> = listOf(
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
    )

    val fakeSavedStateHandle = SavedStateHandle(
        mapOf("items" to mockItems)
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseTypeSelectionScreen(
                        createExpenseViewModel = CreateExpenseViewModel(
                            savedStateHandle = fakeSavedStateHandle
                        ),
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
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
    )

    val fakeSavedStateHandle = SavedStateHandle(
        mapOf("items" to mockItems)
    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseTypeSelectionScreen(
                        createExpenseViewModel = CreateExpenseViewModel(
                            savedStateHandle = fakeSavedStateHandle
                        ),
                        onContinue = {}
                    )
                }
            )
        }
    )
}

@Composable
fun ExpenseTypeSelectionScreen(
    createExpenseViewModel: CreateExpenseViewModel,
    onContinue: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            ExpenseSummaryHeader(
                amount = createExpenseViewModel.createExpenseUiModel.amount,
                itemCount = createExpenseViewModel.createExpenseUiModel.size
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(
                        items = ExpenseTypeEnum.entries,
                        itemContent = { expenseTypeEnum ->
                            Button(
                                onClick = {
                                    createExpenseViewModel.onCreateExpenseUiEvent(
                                        CreateExpenseUiEvent.ExpenseTypeSelected(expenseTypeEnum)
                                    )
                                    onContinue()
                                },
                                modifier = Modifier.aspectRatio(1f),
                                shape = RoundedCornerShape(25),
                                content = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = expenseTypeEnum.icon),
                                            contentDescription = expenseTypeEnum.value
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = expenseTypeEnum.value,
                                            style = MaterialTheme.typography.labelSmall,
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}