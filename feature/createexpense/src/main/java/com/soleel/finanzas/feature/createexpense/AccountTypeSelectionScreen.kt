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
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum


@Composable
fun AccountTypeSelectionScreen(
    createExpenseViewModel: CreateExpenseViewModel,
    onBackPress: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Account Type Selection Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackPress) {
            Text(text = "Back to Expense Name Input")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onContinue) {
            Text(text = "Continue to Account Selection")
        }
    }
}

@Composable
private fun ExpenseSummaryHeader(createExpenseViewModel: CreateExpenseViewModel) {

    // Ejemplos:

    // 🛠 SERVICES (Servicios)
    // Gasté $10.000 en un servicio.
    // Gasté $18.500 en 2 servicios.

    // 🛒 MARKET (Despensa)
    // Gasté $6.700 en un producto de despensa.
    // Gasté $14.250 en 3 productos de despensa.

    // 📦 ACQUISITION (Adquisición)
    // Gasté $12.000 en una adquisición.
    // Gasté $35.800 en 2 adquisiciones.

    // 🎉 LEASURE (Ocio)
    // Gasté $8.500 en un ocio.
    // Gasté $19.000 en 3 ocios.

    // 🎁 GIFT (Regalo)
    // Gasté $15.000 en un regalo.
    // Gasté $28.900 en 2 regalos.

    // 🔁 TRANSFER (Transferencia)
    // Gasté $100.000 en una transacción.
    // Gasté $250.000 en 2 transacciones.

    // ❓ OTHER (Otro)
    // Gasté $9.000 en un item variado.
    // Gasté $21.000 en 3 ítems variados.


    val amount: Float = createExpenseViewModel.createExpenseUiModel.amount
    val itemCount: Int = createExpenseViewModel.items.size
    val type: ExpenseTypeEnum = createExpenseViewModel.createExpenseUiModel.expenseType!!


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
