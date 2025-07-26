package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun CalculatorScreenLongPreview() {
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

@ShortDevicePreview
@Composable
fun CalculatorScreenShortPreview() {
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

    ExpenseSummaryHeader(
        amount = createExpenseViewModel.createExpenseUiModel.amount,
        itemCount = createExpenseViewModel.items.size
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
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
                        modifier = Modifier
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(25),
                        content = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = expenseTypeEnum.icon),
                                    contentDescription = expenseTypeEnum.value
                                )
                                Text(
                                    text = expenseTypeEnum.value,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun ExpenseSummaryHeader(
    amount: Float,
    itemCount: Int,
) {

    // Ejemplos:
    // Gaste $50.000 en 7 items
    // Gaste $1.000 -> 1 item
    // Gaste $300.000 en 2 items

    val summary = buildString {
        append("Gasté $${amount}")
//        if (name.isNotBlank()) append("en $name ")
//        if (!type?.name.isNullOrBlank()) append("($type) ")
//        append("el ${date.format(DateTimeFormatter.ofPattern("dd MMM"))} ")
        append("en $itemCount artículo${if (itemCount != 1) "s" else ""} ")
//        if (!account?.name.isNullOrBlank()) append("usando mi ${account?.name} ")
//        if (!accountType?.name.isNullOrBlank()) append("(${accountType?.name})")
//        if (instalments > 1) append(", en $instalments cuotas")
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.LightGray,
        tonalElevation = 2.dp
    ) {
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}
