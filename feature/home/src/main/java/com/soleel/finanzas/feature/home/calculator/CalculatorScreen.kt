package com.soleel.finanzas.feature.home.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlin.Int
import kotlin.String

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
                modifier = Modifier.padding(paddingValues),
                content = {
                    ItemInCalculator(
                        itemInCalculator = currentItemUi,
                        onNameChanged = { it -> calculatorViewModel.onNameChanged(name = it) }
                    )

                    LazyColumn(
//                        contentPadding = PaddingValues(bottom = 72.dp),
                        content = {
                            itemsInCartUi.forEach(
                                action = { itemInCart: CalculatorUiModel ->
                                    item(
                                        content = {
                                            ItemInCart(itemInCart)
                                        }
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
fun ItemInCalculator(
    itemInCalculator: CalculatorUiModel,
    onNameChanged: (name: String) -> Unit
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CLPCurrencyVisualTransformation())
    })

    Column(
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
                                color = if (!itemInCalculator.historyOperations.isEmpty()) {
                                    Color.Transparent
                                } else {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                        content = {
                            val valueAmount: Int = itemInCalculator.value.toInt()
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
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Multiply
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Multiply) {
                                        Color.Transparent
                                    } else {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            content = {
                                val multiply: String = if (
                                    itemInCalculator
                                        .historyOperations
                                        .lastOrNull() == CalculatorOperatorButtonUiEvent.Multiply &&
                                    itemInCalculator.isNextOperationInitialDecimal
                                ) {
                                    itemInCalculator.multiply.toInt().toString() + "."
                                } else if (itemInCalculator.multiply == 0f) {
                                    ""
                                } else if (itemInCalculator.multiply % 1 != 0f) {
                                    itemInCalculator.multiply.toString()
                                } else {
                                    itemInCalculator.multiply.toInt().toString()
                                }
                                Text(
                                    text = " x $multiply", // TODO: Cambiar esto por una transformacion visual
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Division
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Division) {
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
                                    itemInCalculator
                                        .historyOperations
                                        .lastOrNull() == CalculatorOperatorButtonUiEvent.Division &&
                                    itemInCalculator.isNextOperationInitialDecimal
                                ) {
                                    itemInCalculator.division.toInt().toString() + "."
                                } else if (itemInCalculator.division == 0f) {
                                    ""
                                } else if (itemInCalculator.division % 1 != 0f) {
                                    itemInCalculator.division.toString()
                                } else {
                                    itemInCalculator.division.toInt().toString()
                                }
                                Text(
                                    text = " / $division",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Subtract
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .background( // TODO: Si se ingresa un valor no valido, el background cambia a rojo
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Subtract) {
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
                                    if (itemInCalculator.subtract == 0f) {
                                        ""
                                    } else {
                                        itemInCalculator.subtract.toInt().toString()
                                        val subtractAmount: Int =
                                            itemInCalculator.subtract.toInt()
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
                    val resultAmount: Int = itemInCalculator.result.toInt()
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
                        text = if (itemInCalculator.result < 0f) "- $resultAmountCLP" else resultAmountCLP,
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
                value = itemInCalculator.name,
                onValueChange = { onNameChanged(it) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                enabled = itemInCalculator.name.isNotEmpty() || itemInCalculator.result > 0,
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
                            tint = if (itemInCalculator.name.isNotEmpty() || itemInCalculator.result > 0) {
                                LocalContentColor.current
                            } else {
                                Color.LightGray
                            }
                        )
                        Box(modifier = Modifier.weight(1f)) {
                            if (itemInCalculator.name.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.calculator_item_name_label),
                                    modifier = Modifier
                                        .matchParentSize()
//                                                .background(if (isFocused) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent)
                                        .padding(start = 2.dp), // opcional: para que no toque el borde
                                    style = LocalTextStyle.current.copy(
                                        color = if (itemInCalculator.name.isNotEmpty() || itemInCalculator.result > 0) {
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

//@ShortDevicePreview
@Composable
fun ItemsInCartPreview() {
    val currentItemInCalculator: CalculatorUiModel = CalculatorUiModel(
        name = "item 2",
        value = 25000f,
        multiply = 3f,
        division = 0f,
        subtract = 0f,
        result = 75000f
    )

    val itemsInCartUi: List<CalculatorUiModel> = listOf(
        CalculatorUiModel(
            name = "item 1",
            value = 1000f,
            multiply = 5f,
            division = 2f,
            subtract = 0f,
            result = 0f
        ),
        CalculatorUiModel(
            name = "Comida para perro",
            value = 1000f,
            multiply = 0f,
            division = 0f,
            subtract = 0f,
            result = 0f
        ),
    )

    Column(
//        modifier = Modifier.padding(paddingValues),
        content = {
            ItemInCalculator(
                itemInCalculator = currentItemInCalculator,
                onNameChanged = { }
            )

            LazyColumn(
//                contentPadding = PaddingValues(bottom = 72.dp),
                content = {
                    itemsInCartUi.forEach(
                        action = { itemInCart: CalculatorUiModel ->
                            item(
                                content = {
                                    ItemInCart(itemInCart)
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
fun ItemInCart(itemInCart: CalculatorUiModel) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CLPCurrencyVisualTransformation())
    })

    // TODO: Cambiar esto por una transformacion visual
    val valueAmountCLP: String = currencyVisualTransformation
        .filter(AnnotatedString(text = itemInCart.value.toInt().toString()))
        .text.toString()

    val multiplyFormated: String = if (itemInCart.multiply % 1 != 0f) {
        itemInCart.multiply.toString()
    } else {
        itemInCart.multiply.toInt().toString()
    }

    val divisionFormated: String = if (itemInCart.division % 1 != 0f) {
        itemInCart.division.toString()
    } else {
        itemInCart.division.toInt().toString()
    }

    val subtractAmountCLP: String = currencyVisualTransformation
        .filter(AnnotatedString(text = itemInCart.subtract.toInt().toString()))
        .text.toString()

    val resultAmountCLP: String = currencyVisualTransformation
        .filter(
            AnnotatedString(text = itemInCart.result.toInt().toString())
        )
        .text.toString()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        content = {
            Text(
                text = valueAmountCLP,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            if (itemInCart.multiply != 0f) {
                Text(
                    text = " x $multiplyFormated",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            if (itemInCart.division != 0f) {
                Text(
                    text = " / $divisionFormated",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            if (itemInCart.subtract != 0f) {
                Text(
                    text = " - $subtractAmountCLP",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
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
            Text(
                text = "=",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = if (itemInCart.result < 0f) "- $resultAmountCLP" else resultAmountCLP,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    )

    // TODO: No logro que al presionar 'Back' y tras bajarse el teclado el foco en el
    //  TextField se libere, el cursor sigue marcando el foco.
    // TODO: Cambiar color condicionado a theme
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Create,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp),
            tint = if (itemInCart.name.isNotEmpty() || itemInCart.result > 0f) {
                LocalContentColor.current
            } else {
                Color.LightGray
            }
        )

        Text(
            text = if (itemInCart.name.isNotEmpty()) itemInCart.name
            else stringResource(R.string.calculator_item_name_label),
            modifier = Modifier.weight(1f),
            style = LocalTextStyle.current.copy(
                color = if (itemInCart.name.isNotEmpty() || itemInCart.result > 0) {
                    LocalContentColor.current
                } else {
                    Color.LightGray
                }
            )
        )
    }
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