package com.soleel.finanzas.feature.home.calculator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import kotlinx.coroutines.launch

@LongDevicePreview
@Composable
fun CalculatorScreenLongPreview() {
    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    CalculatorScreen(navigateToCreateSpentGraph = { })
                }
            )
        }
    )
}

@ShortDevicePreview
@Composable
fun CalculatorScreenShortPreview() {
    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    CalculatorScreen(navigateToCreateSpentGraph = { })
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = hiltViewModel(),
    navigateToCreateSpentGraph: (items: List<Item>) -> Unit
) {
    val currentItemUi: CalculatorUiModel = calculatorViewModel.currentCalculatorUiModel
    val itemsInCartUi: List<CalculatorUiModel> = calculatorViewModel.calculatorUiModels

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CLPCurrencyVisualTransformation())
    })

    val bottomSheetScaffoldState: BottomSheetScaffoldState = remember {
        BottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = false, // 👈 EVITAR QUE SE OCULTE COMPLETO
                initialValue = SheetValue.Expanded, // 👈 FORZAR EXPANDIDO INICIAL
                confirmValueChange = { sheetValue ->
                    sheetValue != SheetValue.Hidden // 👈 EVITAR QUE PUEDA USAR EL ESTADO OCULTO
                }
            ),
            snackbarHostState = SnackbarHostState()
        )
    }

    val coroutineScope = rememberCoroutineScope()

    val openResetDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openReplaceDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val selectedItemInCart: MutableState<CalculatorUiModel?> = remember { mutableStateOf<CalculatorUiModel?>(null) }

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
                                        modifier = Modifier.weight(calculatorButton.weight),
                                        value = calculatorButton.value,
                                        isEnabled = calculatorButton.isEnabled,
                                        isNumber = calculatorButton.operator == CalculatorOperatorButtonUiEvent.Number,
                                        onClick = {
                                            if (!openResetDialog.value && calculatorButton.operator is CalculatorOperatorButtonUiEvent.Reset) {
                                                openResetDialog.value = true
                                            } else {
                                                calculatorViewModel.onButtonCalculatorEvent(
                                                    calculatorButton
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Button(
                        onClick = { calculatorViewModel.saveCart(navigateToCreateSpentGraph = navigateToCreateSpentGraph) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(4.dp),
                        enabled = itemsInCartUi.isNotEmpty(),
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
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                content = {
                    ItemInCalculator(
                        itemInCalculator = currentItemUi,
                        onNameChanged = { calculatorViewModel.onNameChanged(name = it) }
                    )

                    if (itemsInCartUi.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Sin items en el carrito",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            content = {
                                itemsIndexed(
                                    items = itemsInCartUi,
                                ) { index, itemInCart ->

                                    ItemInCart(
                                        itemInCart = itemInCart,
                                        index = index,
                                        onSelect = {
                                            if (!openReplaceDialog.value && currentItemUi.value != 0f) {
                                                selectedItemInCart.value = itemInCart
                                                openReplaceDialog.value = true
                                            } else {
                                                calculatorViewModel.onItemInCartEvent(
                                                    ItemInCartUiEvent.Select(itemInCart)
                                                )

                                                coroutineScope.launch {
                                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                                }
                                            }
                                        },
                                        onRemove = {
                                            calculatorViewModel.onItemInCartEvent(
                                                ItemInCartUiEvent.Remove(itemInCart)
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )

    if (openResetDialog.value) {
        AlertDialogCalculator(
            onDismissRequest = { openResetDialog.value = false },
            onConfirmation = {
                openResetDialog.value = false
                calculatorViewModel.onButtonCalculatorEvent(
                    calculatorViewModel.calculatorButtonsUi[0][0]
                )
            },
            dialogTitle = "Reiniciar todo?",
            dialogText = "Hay items en el carrito listos para guardar.",
            icon = Icons.Default.Info
        )
    }

    if (openReplaceDialog.value) {
        AlertDialogCalculator(
            onDismissRequest = { openReplaceDialog.value = false },
            onConfirmation = {
                openReplaceDialog.value = false

                // README: Aqui selectedItemInCart.value nunca va a ser null
                calculatorViewModel.onItemInCartEvent(
                    ItemInCartUiEvent.Select(selectedItemInCart.value!!)
                )

                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            },
            dialogTitle = "Descartar calculadora?",
            dialogText = "Hay un item en la calculadora pendiente de agregarse al carrito.",
            icon = Icons.Default.Info
        )
    }
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
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                content = {
                    Column(
                        modifier = if (itemInCalculator.historyOperations.isNotEmpty()) {
                            Modifier
                                .background(color = Color.Transparent)
                                .padding(end = 4.dp)
                        } else {
                            Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 6.dp)
                        },
                        content = {
                            val valueAmount: Int = itemInCalculator.value.toInt()
                            val valueAmountCLP: String = currencyVisualTransformation
                                .filter(AnnotatedString(text = valueAmount.toString()))
                                .text.toString()

                            Text(
                                text = valueAmountCLP,
                                style = MaterialTheme.typography.headlineSmall,
                                color = if (itemInCalculator.historyOperations.isNotEmpty()) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onPrimary
                                }
                            )
                        }
                    )
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Multiply
                        )
                    ) {
                        Column(
                            modifier = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Multiply) {
                                Modifier
                                    .background(color = Color.Transparent)
                                    .padding(start = 2.dp, end = 4.dp)
                            } else {
                                Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 6.dp)
                            },
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
                                    text = "x $multiply", // TODO: Cambiar esto por una transformacion visual
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Multiply) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            }
                        )
                    }
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Division
                        )
                    ) {
                        Column(
                            modifier = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Division) {
                                Modifier
                                    .background(color = Color.Transparent)
                                    .padding(start = 2.dp, end = 4.dp)
                            } else {
                                Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 6.dp)
                            },
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
                                    text = "/ $division",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Division) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            }
                        )
                    }
                    if (itemInCalculator.historyOperations.contains(
                            CalculatorOperatorButtonUiEvent.Subtract
                        )
                    ) {
                        Column(
                            modifier = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Subtract) {
                                Modifier
                                    .background(color = Color.Transparent)
                                    .padding(start = 2.dp)
                            } else {
                                Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 6.dp)
                            },
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
                                    text = "- $subtractAmountCLP",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = if (itemInCalculator.historyOperations.lastOrNull() != CalculatorOperatorButtonUiEvent.Subtract) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
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
                    .fillMaxWidth(),
//                    .padding(horizontal = 8.dp),
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

            val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
//            val isFocused by interactionSource.collectIsFocusedAsState()

            // TODO: No logro que al presionar 'Back' y tras bajarse el teclado el foco en el
            //  TextField se libere, el cursor sigue marcando el foco.
            // TODO: Cambiar color condicionado a theme
            BasicTextField(
                value = itemInCalculator.name,
                onValueChange = { onNameChanged(it) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(horizontal = 8.dp, vertical = 2.dp),
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

@ShortDevicePreview
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
            result = 2500f
        ),
        CalculatorUiModel(
            name = "Comida para perro",
            value = 1000f,
            multiply = 0f,
            division = 0f,
            subtract = 0f,
            result = 1000f
        ),
        CalculatorUiModel(
            name = "Comida para gato",
            value = 1000f,
            multiply = 0f,
            division = 0f,
            subtract = 500f,
            result = 500f
        ),
    )

    Column(
        content = {
            ItemInCalculator(
                itemInCalculator = currentItemInCalculator,
                onNameChanged = { }
            )

            LazyColumn(
                content = {
                    itemsIndexed(
                        items = itemsInCartUi
                    ) { index, itemInCart ->

                        ItemInCart(
                            itemInCart = itemInCart,
                            index = index,
                            onSelect = {},
                            onRemove = {}
                        )
                    }
                }
            )
        }
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun ItemInCart(
    itemInCart: CalculatorUiModel,
    index: Int,
    onSelect: () -> Unit,
    onRemove: () -> Unit
) {
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

    // TODO:
    //  1.- Implementar que al presionar el icono de delete, este cambie a uno de confirmacion.
    //  2.- Implementar que al estar el estado de confirmacion y presionar en cualquier otro
    //      elemento de la pantalla, este se des-confirme.
//    var enableDelete: Boolean by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp)
            .background(if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .clickable(onClick = onSelect),
                content = {
                    Text(
                        text = itemInCart.name,
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = valueAmountCLP,
                                modifier = Modifier.padding(end = 4.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            if (itemInCart.multiply != 0f) {
                                Text(
                                    text = "x $multiplyFormated",
                                    modifier = Modifier.padding(start = 2.dp, end = 4.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            if (itemInCart.division != 0f) {
                                Text(
                                    text = "/ $divisionFormated",
                                    modifier = Modifier.padding(start = 2.dp, end = 4.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            if (itemInCart.subtract != 0f) {
                                Text(
                                    text = "- $subtractAmountCLP",
                                    modifier = Modifier.padding(start = 2.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 12.dp),
                        content = {
                            Text(
                                text = if (itemInCart.result < 0f) "- $resultAmountCLP" else resultAmountCLP,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                }
            )
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(
                            topStartPercent = 20,
                            topEndPercent = 0,
                            bottomEndPercent = 0,
                            bottomStartPercent = 20
                        )
                    )
                    .fillMaxHeight()
                    .width(60.dp)
                    .clickable(onClick = onRemove),
//                    .clickable(
//                        onClick = {
//                            if (enableDelete) {
//                                onRemove()
//                            } else {
//                                enableDelete = true
//                            }
//                        }
//                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
//                        imageVector = if (enableDelete) Icons.Default.Check else Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
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

@Composable
fun AlertDialogCalculator(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}