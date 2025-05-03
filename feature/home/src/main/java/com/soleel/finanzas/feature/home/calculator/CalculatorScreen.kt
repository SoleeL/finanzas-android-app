package com.soleel.finanzas.feature.home.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@LongDevicePreview
@Composable
fun CalculatorScreenLongPreview() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("finanzas")
                },
                modifier = Modifier.background(Color.DarkGray)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                content = { CalculatorScreen() }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ShortDevicePreview
@Composable
fun CalculatorScreenShortPreview() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("finanzas")
                },
                modifier = Modifier.background(Color.DarkGray)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                content = { CalculatorScreen() }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = hiltViewModel()
) {
    val currentItemUi: CalculatorUiModel = calculatorViewModel.currentCalculatorUiModel
    val itemsInCartUi: List<CalculatorUiModel> = calculatorViewModel.calculatorUiModels

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CLPCurrencyVisualTransformation())
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        content = {

            // TODO: No logro que al presionar 'Back' y tras bajarse el teclado el foco en el
            //  TextField se libere, el cursor sigue marcando el foco.
            OutlinedTextField(
                value = currentItemUi.name,
                onValueChange = { calculatorViewModel.onNameChanged(name = it) },
                modifier = Modifier.fillMaxWidth(),
                enabled = currentItemUi.name.isNotEmpty() || currentItemUi.result > 0,
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
                            imageVector = Icons.Filled.Info,
                            tint = Color.Red, // Cambiar color
                            contentDescription = "Nombre del item a crear"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Nombre del item a crear"
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                content = {
                    Column(
                        modifier = Modifier
                            .background(
                                color = if (!currentItemUi.historyOperations.isEmpty()) {
                                    Color.Transparent
                                } else {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                        content = {
                            val valueAmount: Int = currentItemUi.value.toInt()
                            val valueAmountCLP: String = currencyVisualTransformation
                                .filter(AnnotatedString(text = valueAmount.toString()))
                                .text.toString()

                            Text(
                                text = valueAmountCLP,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    )
                    if (currentItemUi.historyOperations.contains(CalculatorOperatorButtonUiEvent.Multiply)) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = if (currentItemUi.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Multiply) {
                                        Color.Transparent
                                    } else {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                            content = {
                                val multiply: String = if (
                                    currentItemUi
                                        .historyOperations
                                        .lastOrNull() == CalculatorOperatorButtonUiEvent.Multiply &&
                                    currentItemUi.isNextOperationInitialDecimal
                                    ) {
                                    currentItemUi.multiply.toInt().toString() + "."
                                } else if (currentItemUi.multiply == 0f) {
                                    ""
                                } else if (currentItemUi.multiply % 1 != 0f) {
                                    currentItemUi.multiply.toString()
                                } else {
                                    currentItemUi.multiply.toInt().toString()
                                }
                                Text(
                                    text = " x $multiply", // TODO: Cambiar esto por una transformacion visual
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                    if (currentItemUi.historyOperations.contains(CalculatorOperatorButtonUiEvent.Division)) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = if (currentItemUi.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Division) {
                                        Color.Transparent
                                    } else {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                            content = {
                                val division: String = if (
                                    currentItemUi
                                        .historyOperations
                                        .lastOrNull() == CalculatorOperatorButtonUiEvent.Division &&
                                    currentItemUi.isNextOperationInitialDecimal
                                    ) {
                                    currentItemUi.division.toInt().toString() + "."
                                } else if (currentItemUi.division == 0f) {
                                    ""
                                } else if (currentItemUi.division % 1 != 0f) {
                                    currentItemUi.division.toString()
                                } else {
                                    currentItemUi.division.toInt().toString()
                                }
                                Text(
                                    text = " / $division",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                    if (currentItemUi.historyOperations.contains(CalculatorOperatorButtonUiEvent.Subtract)) {
                        Column(
                            modifier = Modifier
                                .background( // TODO: Si se ingresa un valor no valido, el background cambia a rojo
                                    color = if (currentItemUi.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Subtract) {
                                        Color.Transparent
                                    } else {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                            content = {
                                val subtractAmountCLP: String = if (currentItemUi.subtract == 0f) {
                                    ""
                                } else {
                                    currentItemUi.subtract.toInt().toString()
                                    val subtractAmount: Int = currentItemUi.subtract.toInt()
                                    currencyVisualTransformation
                                        .filter(AnnotatedString(text = subtractAmount.toString()))
                                        .text.toString()
                                }

                                Text(
                                    text = " - $subtractAmountCLP",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                content = {
                    val resultAmount: Int = currentItemUi.result.toInt()
                    val resultAmountCLP: String = currencyVisualTransformation
                        .filter(
                            AnnotatedString(text = resultAmount.toString())
                        )
                        .text.toString()

                    Text(
                        text = "=",
                        style = MaterialTheme.typography.displaySmall
                    )

                    Text(
                        text = if (currentItemUi.result < 0f) "- $resultAmountCLP" else resultAmountCLP,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )

            Divider(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                thickness = 2.dp
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                content = {
                    val itemsInCartTotalValue: Double = itemsInCartUi.sumOf(
                        selector = { it.result.toDouble() })
                    val itemsInCartTotalAmountCLP: String = currencyVisualTransformation
                        .filter(AnnotatedString(text = itemsInCartTotalValue.toInt().toString()))
                        .text.toString()

                    Text(
                        text = "Total: ",
                        style = MaterialTheme.typography.displayMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = itemsInCartTotalAmountCLP,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            )

            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()
            var showBottomSheet by remember { mutableStateOf(false) }

            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                content = {
                    // Sheet content
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Hide bottom sheet")
                    }
                }
            )

            calculatorViewModel.calculatorButtonsUi.forEach(
                action = { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            row.forEach(
                                action = { calculatorButton ->
                                    CalculatorButton(
                                        modifier = Modifier.weight(1f),
                                        value = calculatorButton.value,
                                        isEnabled = calculatorButton.isEnabled,
                                        isNumber = calculatorButton.operator == CalculatorOperatorButtonUiEvent.Number,
                                        onClick = { calculatorViewModel.onButtonCalculatorEvent(calculatorButton) }
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

@Composable
fun CalculatorButton(
    modifier: Modifier,
    value: String,
    isEnabled: Boolean,
    isNumber: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        enabled = isEnabled,
        shape = RoundedCornerShape(20),
        colors = if (isNumber) {
            ButtonDefaults.buttonColors()
        } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        },
        content = {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    )
}