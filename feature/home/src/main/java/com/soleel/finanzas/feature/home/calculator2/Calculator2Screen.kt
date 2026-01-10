package com.soleel.finanzas.feature.home.calculator2

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.ShortDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.core.ui.visualtransaformations.CLPCurrencyVisualTransformation
import com.soleel.finanzas.core.ui.visualtransaformations.TwoDecimalTransformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@LongDevicePreview
@Composable
fun Calculator2ScreenLongPreview() {
    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    CalculatorScreen(
                        navigateToCreateExpenseGraph = { _ -> }
                    )
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
                    CalculatorScreen(
                        navigateToCreateExpenseGraph = { _ -> }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    calculatorViewModel: Calculator2ViewModel = hiltViewModel(),
    navigateToCreateExpenseGraph: (items: List<Item>) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isInteractionProcessing by remember { mutableStateOf(false) }

    val currentCalculatorItemUi: CalculatorItemUiModel =
        calculatorViewModel.currentCalculatorItemUiModel
    val cartCalculatorItemsUi: List<CalculatorItemUiModel> =
        calculatorViewModel.cartCalculatorItemsUiModels
    val calculatorButtonsUi: List<CalculatorButtonUiState> =
        calculatorViewModel.calculatorButtonsUi

    val openResetDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openReplaceDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val selectedItemInCart: MutableState<CalculatorItemUiModel?> = remember {
        mutableStateOf<CalculatorItemUiModel?>(null)
    }

    val bottomSheetScaffoldState: BottomSheetScaffoldState = remember {
        BottomSheetScaffoldState(
            bottomSheetState = SheetState(
                // skipPartiallyExpanded: EVITAR QUE SE OCULTE COMPLETO
                skipPartiallyExpanded = false,

                // initialValue: FORZAR EXPANDIDO INICIAL
                initialValue = SheetValue.Expanded,

                confirmValueChange = { sheetValue ->
                    // confirmValueChange: EVITAR QUE PUEDA USAR EL ESTADO OCULTO
                    sheetValue != SheetValue.Hidden
                }
            ),
            snackbarHostState = SnackbarHostState()
        )
    }

    BottomSheetScaffold(
        sheetContent = {
            CalculatorKeyboard(
                calculatorButtonsUi = calculatorButtonsUi,
                isInteractionProcessing = isInteractionProcessing,
                onInteractionStart = { isInteractionProcessing = true },
                onInteractionEnd = {
                    coroutineScope.launch {
                        delay(100)
                        isInteractionProcessing = false
                    }
                }
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            CalculatorContent(
                paddingValues = paddingValues,
                currentItemUi = currentCalculatorItemUi,
                itemsInCartUi = cartCalculatorItemsUi,
                openReplaceDialog = openReplaceDialog,
                selectedItemInCart = selectedItemInCart,
                calculatorViewModel = calculatorViewModel,
                bottomSheetScaffoldState = bottomSheetScaffoldState
            )
        }
    )
}

@Composable
fun CalculatorKeyboard(
    calculatorButtonsUi: List<CalculatorButtonUiState>,
    isInteractionProcessing: Boolean,
    onInteractionStart: () -> Unit,
    onInteractionEnd: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        items(
            items = calculatorButtonsUi,
            span = { GridItemSpan(it.span) }
        ) { calculatorButton ->
            CalculatorKeyboardButton(
                button = calculatorButton,
                isInteractionProcessing = isInteractionProcessing,
                onInteractionStart = onInteractionStart,
                onInteractionEnd = onInteractionEnd
            )
        }
    }
}

@Composable
fun CalculatorKeyboardButton(
    button: CalculatorButtonUiState,
    isInteractionProcessing: Boolean,
    onInteractionStart: () -> Unit,
    onInteractionEnd: () -> Unit
) {
    Button(
        onClick = {
            if (!isInteractionProcessing) {
                onInteractionStart()
                button.onClick()
                onInteractionEnd()
            }
        },
        modifier = Modifier
            .aspectRatio(if (button.span == 1) 1f else 1f * button.span)
            .fillMaxWidth()
            .padding(6.dp),
        enabled = button.isEnabledEvaluator(),
        colors = when (button.type) {
            CalculatorButtonType.DIGIT ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            CalculatorButtonType.OPERATION ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )

            CalculatorButtonType.FUNCTION ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
        },
        contentPadding = PaddingValues(0.dp)
    ) {
        if (button.icon != null) {
            Icon(
                painter = painterResource(id = button.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Text(
                text = button.value,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorContent(
    paddingValues: PaddingValues,
    currentItemUi: CalculatorItemUiModel,
    itemsInCartUi: List<CalculatorItemUiModel>,
    openReplaceDialog: MutableState<Boolean>,
    selectedItemInCart: MutableState<CalculatorItemUiModel?>,
    calculatorViewModel: Calculator2ViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()

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
                                    if (!openReplaceDialog.value && currentItemUi.value != 0L) {
                                        selectedItemInCart.value = itemInCart
                                        openReplaceDialog.value = true
                                    } else {
                                        calculatorViewModel.onItemInCartEvent(
                                            CalculatorItemInCartUiEvent.Select(itemInCart)
                                        )

                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        }
                                    }
                                },
                                onRemove = {
                                    calculatorViewModel.onItemInCartEvent(
                                        CalculatorItemInCartUiEvent.Remove(itemInCart)
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

@Composable
fun ItemInCalculator(
    itemInCalculator: CalculatorItemUiModel,
    onNameChanged: (name: String) -> Unit
) {
    val currencyVisualTransformation by remember(
        calculation = {
            mutableStateOf(CLPCurrencyVisualTransformation())
        }
    )

    val twoDecimalTransformation by remember(
        calculation = {
            mutableStateOf(TwoDecimalTransformation())
        }
    )

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
                        modifier = if (itemInCalculator.buttonsHistory.isNotEmpty()) {
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
                                color = if (itemInCalculator.buttonsHistory.isNotEmpty()) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onPrimary
                                }
                            )
                        }
                    )

                    val lastButtonOperator: CalculatorButtonUiEvent? =
                        itemInCalculator.buttonsHistory
                            .lastOrNull {
                                it != CalculatorButtonUiEvent.Decimal
                            }

                    if (itemInCalculator.buttonsHistory.contains(CalculatorButtonUiEvent.Multiply)) {
                        Column(
                            modifier = if (lastButtonOperator != CalculatorButtonUiEvent.Multiply) {
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
                                val multiplyTwoDecimal: String = twoDecimalTransformation
                                    .filter(AnnotatedString(text = itemInCalculator.multiply.toString()))
                                    .text.toString()

                                Text(
                                    text = "x $multiplyTwoDecimal",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = if (lastButtonOperator != CalculatorButtonUiEvent.Multiply) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            }
                        )
                    }
                    if (itemInCalculator.buttonsHistory.contains(
                            CalculatorButtonUiEvent.Division
                        )
                    ) {
                        Column(
                            modifier = if (lastButtonOperator != CalculatorButtonUiEvent.Division) {
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
                                        .buttonsHistory
                                        .lastOrNull() == CalculatorButtonUiEvent.Division &&
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
                                    color = if (lastButtonOperator != CalculatorButtonUiEvent.Division) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            }
                        )
                    }
                    if (itemInCalculator.buttonsHistory.contains(CalculatorButtonUiEvent.Subtract)) {
                        Column(
                            modifier = if (lastButtonOperator != CalculatorButtonUiEvent.Subtract) {
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
                                    if (itemInCalculator.subtract == 0L) {
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
                                    color = if (lastButtonOperator != CalculatorButtonUiEvent.Subtract) {
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

            val interactionSource: MutableInteractionSource =
                remember { MutableInteractionSource() }
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
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
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

@SuppressLint("RememberReturnType")
@Composable
fun ItemInCart(
    itemInCart: CalculatorItemUiModel,
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

                            if (itemInCart.subtract != 0L) {
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