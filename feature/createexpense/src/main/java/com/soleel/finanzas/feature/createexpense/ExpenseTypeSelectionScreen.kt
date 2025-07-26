package com.soleel.finanzas.feature.createexpense

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseTypeSelectionScreen(
    createExpenseViewModel: CreateExpenseViewModel,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ExpenseSummaryHeader(createExpenseViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onContinue) {
            Text(text = "Continue to Expense Date Selection")
        }
    }
}

@Composable
private fun ExpenseSummaryHeader(createExpenseViewModel: CreateExpenseViewModel) {

    // Ejemplos:
    // Gaste $50.000 en 7 items
    // Gaste $1.000 -> 1 item
    // Gaste $300.000 en 2 items

    val amount: Float = createExpenseViewModel.createExpenseUiModel.amount
    val itemCount: Int = createExpenseViewModel.items.size

//    val name: String = createExpenseViewModel.createExpenseUiModel.name
//    val type: ExpenseTypeEnum? = createExpenseViewModel.createExpenseUiModel.expenseType
//    val date: LocalDateTime = createExpenseViewModel.createExpenseUiModel.expenseDate
//
//    val account: Account? = createExpenseViewModel.createExpenseUiModel.account
//    val accountType: AccountTypeEnum? = createExpenseViewModel.createExpenseUiModel.accountType
//    val instalments: Int = createExpenseViewModel.createExpenseUiModel.instalments






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
