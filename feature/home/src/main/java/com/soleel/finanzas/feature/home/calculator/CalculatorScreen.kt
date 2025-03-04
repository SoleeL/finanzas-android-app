package com.soleel.finanzas.feature.home.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.utils.SmartphonePreview

@SmartphonePreview
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}

@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = hiltViewModel()
) {
    val currentItemUi: ItemUi = calculatorViewModel.currentItemUi
    val itemsInCartUi: List<ItemUi> = calculatorViewModel.itemsInCartUi

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CLPCurrencyVisualTransformation())
    })

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (currentItemUi.isMultiply) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(
                                text = "Cantidad",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            BasicTextField(
                                enabled = false,
                                value = currentItemUi.quantity.toString(),
                                onValueChange = { },
                                modifier = Modifier.width(IntrinsicSize.Min),
                                textStyle = MaterialTheme.typography.headlineSmall,
                                singleLine = true
                            )
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (currentItemUi.isMultiply || currentItemUi.isSubtract) Color.Transparent else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            val currentItemValueAmountCLP: String = currencyVisualTransformation
                                .filter(
                                    AnnotatedString(text = (currentItemUi.value).toString())
                                )
                                .text.toString()

                            Text(
                                text = "Producto",
                                style = MaterialTheme.typography.headlineSmall,
                            )

                            Text(
                                text = currentItemValueAmountCLP,
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (currentItemUi.isSubtract) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            val currentItemSubtractAmountCLP: String = currencyVisualTransformation
                                .filter(
                                    AnnotatedString(text = (currentItemUi.subtract).toString())
                                )
                                .text.toString()

                            Text(
                                text = "Desc.",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Text(
                                text = currentItemSubtractAmountCLP,
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            val currentItemTotalAmountCLP: String = currencyVisualTransformation
                                .filter(
                                    AnnotatedString(text = ((currentItemUi.quantity * currentItemUi.value) - currentItemUi.subtract).toString())
                                )
                                .text.toString()

                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.headlineLarge
                            )

                            Text(
                                text = currentItemTotalAmountCLP,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    )
                }
            )

            // TODO: Falta que esto suelte el foco cuando se presione hacia atras/ocultar o en otra elemento de la vista
            OutlinedTextField(
                value = currentItemUi.name,
                onValueChange = { calculatorViewModel.onNameChanged(name = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = currentItemUi.value > 0,
                label = { Text(text = stringResource(R.string.calculator_item_name_label)) },
                supportingText = {
                    if (currentItemUi.nameError != null) Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = currentItemUi.nameError),
                        textAlign = TextAlign.End,
                    )
                },
                trailingIcon = {
                    if (currentItemUi.nameError != null) {
                        Icon(
                            imageVector = Icons.Filled.Info, tint = Color.Red, // Cambiar color
                            contentDescription = "Nombre de la transaccion a crear"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Create,
//                            tint = Color.LightGray, // Cambiar color
                            contentDescription = "Nombre de la transaccion a crear"
                        )
                    }
                },
                isError = currentItemUi.nameError != null,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                )
            )

            buttons.forEach(
                action = { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        content = {
                            row.forEach(
                                action = { button ->
                                    CalculatorButton(
                                        button = button,
                                        currentItemUi = currentItemUi,
                                        onClick = {
                                            calculatorViewModel.onButtonCalculatorEvent(button)
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            )
                        }
                    )
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Button(
                        onClick = { calculatorViewModel.addTransaction() },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(4.dp),
                        content = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    val itemsInCartTotalAmountCLP: String =
                                        currencyVisualTransformation.filter(
                                            AnnotatedString(
                                                text = itemsInCartUi.sumOf(selector = { it.value })
                                                    .toString()
                                            )
                                        ).text.toString()

                                    Text(
                                        text = "GUARDAR",
                                        style = MaterialTheme.typography.headlineMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = itemsInCartTotalAmountCLP,
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            )
                        },
                        shape = CircleShape
                    )
                }
            )
        }
    )
}

val buttons: List<List<CalculatorButtonEventUi>> = listOf(
    listOf(
        CalculatorButtonEventUi.Number(7),
        CalculatorButtonEventUi.Number(8),
        CalculatorButtonEventUi.Number(9),
        CalculatorButtonEventUi.Clear
    ), listOf(
        CalculatorButtonEventUi.Number(4),
        CalculatorButtonEventUi.Number(5),
        CalculatorButtonEventUi.Number(6),
        CalculatorButtonEventUi.Multiply
    ), listOf(
        CalculatorButtonEventUi.Number(1),
        CalculatorButtonEventUi.Number(2),
        CalculatorButtonEventUi.Number(3),
        CalculatorButtonEventUi.Subtract
    ), listOf(
        CalculatorButtonEventUi.Delete,
        CalculatorButtonEventUi.Number(0),
        CalculatorButtonEventUi.Decimal,
        CalculatorButtonEventUi.Add
    )
)

val operatorButtons: List<CalculatorButtonEventUi> = listOf(
    CalculatorButtonEventUi.Clear,
    CalculatorButtonEventUi.Multiply,
    CalculatorButtonEventUi.Subtract,
    CalculatorButtonEventUi.Add,
    CalculatorButtonEventUi.Delete,
    CalculatorButtonEventUi.Decimal
)

@Composable
fun CalculatorButton(
    button: CalculatorButtonEventUi, currentItemUi: ItemUi, onClick: () -> Unit, modifier: Modifier
) {
    // TODO Ocultar boton ',' (o ESPECIAL) cuando se esta en el valor del producto
    Button(onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        enabled = when (button) {
            is CalculatorButtonEventUi.Number -> true
            is CalculatorButtonEventUi.Clear, is CalculatorButtonEventUi.Multiply, is CalculatorButtonEventUi.Subtract, is CalculatorButtonEventUi.Add, is CalculatorButtonEventUi.Delete -> currentItemUi.value > 0

            is CalculatorButtonEventUi.Decimal -> currentItemUi.quantity > 0
        },
        shape = CircleShape,
        colors = if (button in operatorButtons) {
            ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors()
        },
        content = {
            Text(
                text = when (button) {
                    is CalculatorButtonEventUi.Number -> button.value.toString()
                    is CalculatorButtonEventUi.Clear -> "C"
                    is CalculatorButtonEventUi.Multiply -> "*"
                    is CalculatorButtonEventUi.Subtract -> "-"
                    is CalculatorButtonEventUi.Add -> "+"
                    is CalculatorButtonEventUi.Delete -> "<-"
                    is CalculatorButtonEventUi.Decimal -> ","
                },
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    )
}