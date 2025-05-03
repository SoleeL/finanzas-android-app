package com.soleel.finanzas.feature.home.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@LongDevicePreview
@Composable
fun CalculatorScreenLongPreview() {
    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    CalculatorScreen()
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ShortDevicePreview
@Composable
fun CalculatorScreenShortPreview() {
    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    CalculatorScreen()
                }
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

    val bottomSheetScaffoldState: BottomSheetScaffoldState = remember {
        BottomSheetScaffoldState(
            bottomSheetState = SheetState(
                initialValue = SheetValue.Expanded, // 👈 FORZAR EXPANDIDO INICIAL
                skipPartiallyExpanded = false, // 👈 EVITAR QUE SE OCULTE COMPLETO
                confirmValueChange = { sheetValue ->
                    sheetValue != SheetValue.Hidden // 👈 EVITAR QUE PUEDA USAR EL ESTADO OCULTO
                }
            ),
            snackbarHostState = SnackbarHostState()
        )
    }

    BottomSheetScaffold(
        sheetContent = {
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
                                        onClick = {
                                            calculatorViewModel.onButtonCalculatorEvent(
                                                calculatorButton
                                            )
                                        }
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
                        onClick = {
                            calculatorViewModel.onButtonCalculatorEvent(
                                calculatorViewModel.calculatorButtonsUi[0][0]
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(4.dp),
                        enabled = calculatorViewModel.calculatorButtonsUi[0][0].isEnabled,
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        content = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    val itemsInCartTotalValue: Double = itemsInCartUi.sumOf(
                                        selector = { it.result.toDouble() })
                                    val itemsInCartTotalAmountCLP: String =
                                        currencyVisualTransformation
                                            .filter(
                                                AnnotatedString(
                                                    text = itemsInCartTotalValue.toInt().toString()
                                                )
                                            )
                                            .text.toString()

                                    Text(
                                        text = "GUARDAR",
                                        style = MaterialTheme.typography.headlineSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = itemsInCartTotalAmountCLP,
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            )
                        }
                    )
                }
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
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
                            if (currentItemUi.historyOperations.contains(
                                    CalculatorOperatorButtonUiEvent.Multiply
                                )
                            ) {
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
                                        .padding(
                                            start = 4.dp,
                                            end = 4.dp,
                                            top = 2.dp,
                                            bottom = 2.dp
                                        ),
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
                            if (currentItemUi.historyOperations.contains(
                                    CalculatorOperatorButtonUiEvent.Division
                                )
                            ) {
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
                                        .padding(
                                            start = 4.dp,
                                            end = 4.dp,
                                            top = 2.dp,
                                            bottom = 2.dp
                                        ),
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
                            if (currentItemUi.historyOperations.contains(
                                    CalculatorOperatorButtonUiEvent.Subtract
                                )
                            ) {
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
                                        .padding(
                                            start = 4.dp,
                                            end = 4.dp,
                                            top = 2.dp,
                                            bottom = 2.dp
                                        ),
                                    content = {
                                        val subtractAmountCLP: String =
                                            if (currentItemUi.subtract == 0f) {
                                                ""
                                            } else {
                                                currentItemUi.subtract.toInt().toString()
                                                val subtractAmount: Int =
                                                    currentItemUi.subtract.toInt()
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
                            .padding(horizontal = 8.dp),
                        content = {
                            val resultAmount: Int = currentItemUi.result.toInt()
                            val resultAmountCLP: String = currencyVisualTransformation
                                .filter(
                                    AnnotatedString(text = resultAmount.toString())
                                )
                                .text.toString()

                            Text(
                                text = "=",
                                style = MaterialTheme.typography.headlineLarge
                            )

                            Text(
                                text = if (currentItemUi.result < 0f) "- $resultAmountCLP" else resultAmountCLP,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    )

                    val interactionSource = remember { MutableInteractionSource() }
                    val isFocused by interactionSource.collectIsFocusedAsState()

                    // TODO: No logro que al presionar 'Back' y tras bajarse el teclado el foco en el
                    //  TextField se libere, el cursor sigue marcando el foco.
                    // TODO: Cambiar color condicionado a theme
                    BasicTextField(
                        value = currentItemUi.name,
                        onValueChange = { calculatorViewModel.onNameChanged(name = it) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        enabled = currentItemUi.name.isNotEmpty() || currentItemUi.result > 0,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        interactionSource = interactionSource,
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp),
                                    tint = if (currentItemUi.name.isNotEmpty() || currentItemUi.result > 0) {
                                        LocalContentColor.current
                                    } else {
                                        Color.LightGray
                                    }
                                )
                                Box(modifier = Modifier.weight(1f)) {
                                    if (currentItemUi.name.isEmpty()) {
                                        Text(
                                            text = stringResource(R.string.calculator_item_name_label),
                                            modifier = Modifier
                                                .matchParentSize()
//                                                .background(if (isFocused) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent)
                                                .padding(start = 2.dp), // opcional: para que no toque el borde
                                            style = LocalTextStyle.current.copy(
                                                color = if (currentItemUi.name.isNotEmpty() || currentItemUi.result > 0) {
                                                    LocalContentColor.current
                                                } else {
                                                    Color.LightGray
                                                }
                                            )
                                        )
                                    }
                                    innerTextField()
                                }
                            }
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
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    )
}