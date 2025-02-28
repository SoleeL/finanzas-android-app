package com.soleel.finanzas.feature.home.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.ui.theme.SmartphonePreview

@SmartphonePreview
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}

@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        content = {
            BasicTextField(
                enabled = false,
                value = calculatorViewModel.currentItemUi.value.toString(),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    textAlign = TextAlign.End
                ),
                visualTransformation = CLPCurrencyVisualTransformation(),
                singleLine = true
            )

            // Renderizar los botones de la calculadora
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { button ->
                        CalculatorButton(button, onClick = { calculatorViewModel.onItemEventUi(button) })
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Button(
                        onClick = { calculatorViewModel.calculateResult() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        content = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Text(
                                        text = "AGREGAR",
                                        style = MaterialTheme.typography.titleLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = calculatorViewModel.currentItemUi.value.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(start = 8.dp)
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

val buttons = listOf(
    listOf(CalculatorButton.Number(7), CalculatorButton.Number(8), CalculatorButton.Number(9), CalculatorButton.Clear),
    listOf(CalculatorButton.Number(4), CalculatorButton.Number(5), CalculatorButton.Number(6), CalculatorButton.Operator("*")),
    listOf(CalculatorButton.Number(1), CalculatorButton.Number(2), CalculatorButton.Number(3), CalculatorButton.Operator("+")),
    listOf(CalculatorButton.Delete, CalculatorButton.Number(0), CalculatorButton.Decimal, CalculatorButton.Operator("+"))
)

@Composable
fun CalculatorButton(button: CalculatorButton, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = when (button) {
                is CalculatorButton.Number -> button.value.toString()
                is CalculatorButton.Operator -> button.symbol
                is CalculatorButton.Clear -> "C"
                is CalculatorButton.Delete -> "<-"
                is CalculatorButton.Decimal -> ","
            },
            fontSize = 24.sp
        )
    }
}