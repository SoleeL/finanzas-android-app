package com.soleel.finanzas.feature.home.calculator2

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.soleel.finanzas.feature.home.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.floor

// README: Recomendado segun traduccion
// UI Model: Representa un objeto de dominio ya adaptado para mostrarse en la UI.
// UI Event: Son las acciones que provienen desde la UI hacia el ViewModel.
//      Se suelen modelar como sealed classes para representar eventos como clics, scroll, cambios,
//      etc.
// UI State: Representa todo el estado actual de la pantalla.
//      Puede contener:
//          Lista de ítems (itemsInCartUi)
//          Estado de botones (isEnabled)
//          Cargas (isLoading)
//          Errores (errorMessage)
//          Campos de entrada (inputText)
// UI Effect: Para cosas que no deben sobrevivir recomposiciones como mostrar un toast, navegar,
//  abrir diálogos.
//      Son acciones de una sola vez, no estado persistente.

data class CalculatorItemUiModel(
    val name: String = "",
    val nameError: Int? = null,

    val value: Long = 0,

    // README: multiple y division -> No puede ser 1 como multiplo neutro por un tema de historial
    //  de digitos
    val multiply: Float = 0f,

    val division: Float = 0f,

    val subtract: Long = 0,

    val isNextOperationInitialDecimal: Boolean = false,

    val buttonsHistory: List<CalculatorButtonUiEvent> = emptyList(),

    val result: Float = 0f
)

sealed class CalculatorButtonUiEvent {
    data object Multiply : CalculatorButtonUiEvent()
    data object Division : CalculatorButtonUiEvent()
    data object Subtract : CalculatorButtonUiEvent()

    data object Decimal : CalculatorButtonUiEvent()
}

sealed class CalculatorItemInCartUiEvent {
    data class Select(val calculatorUiModel: CalculatorItemUiModel) : CalculatorItemInCartUiEvent()
    data class Remove(val calculatorUiModel: CalculatorItemUiModel) : CalculatorItemInCartUiEvent()
}

enum class CalculatorButtonType {
    DIGIT,
    OPERATION,
    FUNCTION
}

data class CalculatorButtonUiState(
    val value: String,
    @DrawableRes val icon: Int? = null,
    val span: Int = 1,
    val type: CalculatorButtonType,
    val isEnabledEvaluator: () -> Boolean = { true },
    val onClick: () -> Unit
)

@HiltViewModel
class Calculator2ViewModel @Inject constructor(

) : ViewModel() {

    // TODO:
//    companion object {
    // With decimals -> centimos
//        const val MAX_AMOUNT_BY_VALUE: Long = 999999999_00
//        const val MAX_AMOUNT_BY_MULTIPLY: Int = 99_00
//        const val MAX_AMOUNT_BY_DIVISION: Int = 99_00
//        const val MAX_AMOUNT_BY_SUBTRACT: Long = 999999999_00
//    }

    companion object {
        const val MAX_AMOUNT_BY_VALUE: Long = 999999999
        const val MAX_AMOUNT_BY_MULTIPLY: Float = 99.99f // README: Representacion decimal
        const val MAX_AMOUNT_BY_DIVISION: Float = 99.99f // README: Representacion decimal
        const val MAX_AMOUNT_BY_SUBTRACT: Long = 999999999
    }

    private var _currentCalculatorItemUiModel: CalculatorItemUiModel by mutableStateOf(
        CalculatorItemUiModel()
    )
    val currentCalculatorItemUiModel: CalculatorItemUiModel get() = _currentCalculatorItemUiModel

    fun onNameChanged(name: String) {
        _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(name = name)
    }

    private var _cartCalculatorItemsUiModels: List<CalculatorItemUiModel> by mutableStateOf(
        emptyList()
    )
    val cartCalculatorItemsUiModels: List<CalculatorItemUiModel> get() = _cartCalculatorItemsUiModels

    fun onItemInCartEvent(event: CalculatorItemInCartUiEvent) {
        when (event) {
            is CalculatorItemInCartUiEvent.Select -> selectItemInCart(event.calculatorUiModel)
            is CalculatorItemInCartUiEvent.Remove -> removeItemInCart(event.calculatorUiModel)
        }
    }

    private fun selectItemInCart(calculatorUiModel: CalculatorItemUiModel) {
        removeItemInCart(calculatorUiModel)
        _currentCalculatorItemUiModel = calculatorUiModel.copy()
    }

    private fun removeItemInCart(calculatorUiModel: CalculatorItemUiModel) {
        _cartCalculatorItemsUiModels = cartCalculatorItemsUiModels - calculatorUiModel
    }

    private var _calculatorHorizontalButtonsUi: List<CalculatorButtonUiState> by mutableStateOf(
        listOf(
            CalculatorButtonUiState(
                value = "A/C",
                type = CalculatorButtonType.FUNCTION,
                isEnabledEvaluator = {
                    _currentCalculatorItemUiModel.value > 0 &&
                            _cartCalculatorItemsUiModels.isNotEmpty()
                },
                onClick = {
                    _currentCalculatorItemUiModel = CalculatorItemUiModel()
                    _cartCalculatorItemsUiModels = emptyList()
                }
            ),
            CalculatorButtonUiState(
                value = "C",
                type = CalculatorButtonType.FUNCTION,
                isEnabledEvaluator = { _currentCalculatorItemUiModel.value > 0 },
                onClick = { _currentCalculatorItemUiModel = CalculatorItemUiModel() }
            ),
            CalculatorButtonUiState( // TODO
                value = "%",
                type = CalculatorButtonType.FUNCTION,
                isEnabledEvaluator = { true },
                onClick = { }
            ),
            CalculatorButtonUiState(
                value = "<-",
                type = CalculatorButtonType.FUNCTION,
                isEnabledEvaluator = { currentCalculatorItemUiModel.value > 0 },
                onClick = {
                    val last: CalculatorButtonUiEvent? = currentCalculatorItemUiModel
                        .buttonsHistory.lastOrNull {
                            it != CalculatorButtonUiEvent.Decimal
                        }

                    val newCalculatorItemUiModel = when (last) {
                        CalculatorButtonUiEvent.Multiply -> {
                            val currentMultiply: Float = currentCalculatorItemUiModel.multiply
                            val currentMultiplyString: String = currentMultiply.toString()

                            Log.d(
                                BuildConfig.LIBRARY_PACKAGE_NAME,
                                "currentMultiplyString: $currentMultiplyString"
                            )

                            Log.d(
                                BuildConfig.LIBRARY_PACKAGE_NAME,
                                "currentMultiplyString.length == 1: ${currentMultiplyString.length == 1}"
                            )
                            if (currentMultiplyString.length == 1) {
                                currentCalculatorItemUiModel.copy(
                                    multiply = 0f, // Multiplo neutral
                                    buttonsHistory = currentCalculatorItemUiModel
                                        .buttonsHistory.dropLast(1)
                                )
                            } else {
                                var newMultiplyString: String = currentMultiply.toString()
                                    .dropLast(1)

                                Log.d(
                                    BuildConfig.LIBRARY_PACKAGE_NAME,
                                    "newMultiplyString: $newMultiplyString"
                                )

                                val removeDecimal: Boolean = when {
                                    (currentCalculatorItemUiModel.buttonsHistory.lastOrNull() == CalculatorButtonUiEvent.Decimal &&
                                            newMultiplyString.last() == '.') -> {
                                        newMultiplyString = newMultiplyString.dropLast(1)
                                        true
                                    }

                                    (newMultiplyString.last() == '.') -> {
                                        newMultiplyString = newMultiplyString.dropLast(2)
                                        false
                                    }

                                    else -> { // QUEDO AQUI SIN MOTIVO...
                                        false
                                    }
                                }

                                Log.d(
                                    BuildConfig.LIBRARY_PACKAGE_NAME,
                                    "newMultiplyString: $newMultiplyString"
                                )
                                Log.d(
                                    BuildConfig.LIBRARY_PACKAGE_NAME,
                                    "removeDecimal: $removeDecimal"
                                )

                                currentCalculatorItemUiModel.copy(
                                    multiply = newMultiplyString.toFloat(),
                                    buttonsHistory = if (removeDecimal) {
                                        currentCalculatorItemUiModel.buttonsHistory.dropLast(1)
                                    } else {
                                        currentCalculatorItemUiModel.buttonsHistory
                                    }
                                )
                            }
                        }

                        CalculatorButtonUiEvent.Division -> {
                            // TODO
                            val newDivision: Float = currentCalculatorItemUiModel.division / 10
                            if (newDivision == 0f) {
                                currentCalculatorItemUiModel.copy(
                                    division = 0f, // Divisor neutral
                                    buttonsHistory = currentCalculatorItemUiModel
                                        .buttonsHistory.dropLast(1)
                                )
                            } else {
                                currentCalculatorItemUiModel.copy(division = newDivision)
                            }
                        }

                        CalculatorButtonUiEvent.Subtract -> {
                            // TODO
                            val newSubtract: Long = currentCalculatorItemUiModel.subtract / 10L
                            if (newSubtract == 0L) {
                                currentCalculatorItemUiModel.copy(
                                    subtract = 0L, // Restro neutral
                                    buttonsHistory = currentCalculatorItemUiModel
                                        .buttonsHistory.dropLast(1)
                                )
                            } else {
                                currentCalculatorItemUiModel.copy(subtract = newSubtract)
                            }
                        }

                        else -> {
                            val newValue = currentCalculatorItemUiModel.value / 10
                            currentCalculatorItemUiModel.copy(value = newValue)
                        }
                    }

                    _currentCalculatorItemUiModel = newCalculatorItemUiModel
                }
            ),

            CalculatorButtonUiState(
                value = "7",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 7) },
                onClick = { appendDigit(digit = 7) }
            ),
            CalculatorButtonUiState(
                value = "8",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 8) },
                onClick = { appendDigit(digit = 8) }
            ),
            CalculatorButtonUiState(
                value = "9",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 9) },
                onClick = { appendDigit(digit = 9) }
            ),
            CalculatorButtonUiState(
                value = "x",
                type = CalculatorButtonType.OPERATION,
                isEnabledEvaluator = {
                    currentCalculatorItemUiModel.value > 0 &&
                            currentCalculatorItemUiModel.buttonsHistory.lastOrNull() != CalculatorButtonUiEvent.Multiply
                },
                onClick = {
                    _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(
                        buttonsHistory = currentCalculatorItemUiModel.buttonsHistory
                                + CalculatorButtonUiEvent.Multiply
                    )
                }
            ),

            CalculatorButtonUiState(
                value = "4",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 4) },
                onClick = { appendDigit(digit = 4) }
            ),
            CalculatorButtonUiState(
                value = "5",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 5) },
                onClick = { appendDigit(digit = 5) }
            ),
            CalculatorButtonUiState(
                value = "6",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 6) },
                onClick = { appendDigit(digit = 6) }
            ),
            CalculatorButtonUiState(
                value = "/",
                type = CalculatorButtonType.OPERATION,
                isEnabledEvaluator = { true },
                onClick = {
                    _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(
                        buttonsHistory = currentCalculatorItemUiModel.buttonsHistory
                                + CalculatorButtonUiEvent.Division
                    )
                }
            ),

            CalculatorButtonUiState(
                value = "1",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 1) },
                onClick = { appendDigit(digit = 1) }
            ),
            CalculatorButtonUiState(
                value = "2",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 2) },
                onClick = { appendDigit(digit = 2) }
            ),
            CalculatorButtonUiState(
                value = "3",
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = { canAppendDigit(digit = 3) },
                onClick = { appendDigit(digit = 3) }
            ),
            CalculatorButtonUiState(
                value = "-",
                type = CalculatorButtonType.OPERATION,
                isEnabledEvaluator = { true },
                onClick = {
                    _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(
                        buttonsHistory = currentCalculatorItemUiModel.buttonsHistory
                                + CalculatorButtonUiEvent.Subtract
                    )
                }
            ),

            CalculatorButtonUiState(
                value = "0",
                span = 2,
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = {
                    // TODO: deshabilitar cuando es el primer digito de la multiplicacion o division
                    canAppendDigit(digit = 0)
                },
                onClick = { appendDigit(digit = 0) }
            ),
            CalculatorButtonUiState(
                value = ".",
                span = 1,
                type = CalculatorButtonType.DIGIT,
                isEnabledEvaluator = {
                    currentCalculatorItemUiModel.buttonsHistory.isNotEmpty() &&
                            currentCalculatorItemUiModel.buttonsHistory.lastOrNull() != CalculatorButtonUiEvent.Decimal
                },
                onClick = {
                    _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(
                        buttonsHistory = currentCalculatorItemUiModel.buttonsHistory
                                + CalculatorButtonUiEvent.Decimal
                    )
                }
            ),
            CalculatorButtonUiState(
                value = "=", // Add
                span = 1,
                type = CalculatorButtonType.OPERATION,
                isEnabledEvaluator =
                    { true },
                onClick = {
                    _currentCalculatorItemUiModel = currentCalculatorItemUiModel.copy(
                        buttonsHistory = currentCalculatorItemUiModel.buttonsHistory
                                + CalculatorButtonUiEvent.Decimal
                    )
                }
            ),

            CalculatorButtonUiState(
                value = "Guardar", // Navegar a siguiente proceso
                span = 4,
                type = CalculatorButtonType.FUNCTION,
                isEnabledEvaluator =
                    { true },
                onClick = {}
            )
        )
    )

    val calculatorButtonsUi: List<CalculatorButtonUiState> get() = _calculatorHorizontalButtonsUi

    fun canAppendDigit(digit: Int): Boolean {
        val lastButtonOperator: CalculatorButtonUiEvent? = currentCalculatorItemUiModel
            .buttonsHistory.lastOrNull {
                it != CalculatorButtonUiEvent.Decimal
            }

        val newValue: Float = when (lastButtonOperator) {
            CalculatorButtonUiEvent.Multiply -> inputMultiply(digit)
            CalculatorButtonUiEvent.Division -> inputDivision(digit)
            CalculatorButtonUiEvent.Subtract -> inputSubtract(digit).toFloat()
            else -> { // value por defecto
                ((currentCalculatorItemUiModel.value * 10) + digit).toFloat()
            }
        }

        return when (lastButtonOperator) {
            CalculatorButtonUiEvent.Multiply -> newValue <= MAX_AMOUNT_BY_MULTIPLY
            CalculatorButtonUiEvent.Division -> newValue <= MAX_AMOUNT_BY_DIVISION
            CalculatorButtonUiEvent.Subtract -> newValue <= MAX_AMOUNT_BY_SUBTRACT
            else -> newValue <= MAX_AMOUNT_BY_VALUE
        }
    }

    fun appendDigit(digit: Int) {
        val lastButtonOperator: CalculatorButtonUiEvent? = currentCalculatorItemUiModel
            .buttonsHistory.lastOrNull {
                it != CalculatorButtonUiEvent.Decimal
            }

        _currentCalculatorItemUiModel = when (lastButtonOperator) {

            CalculatorButtonUiEvent.Multiply -> {
                currentCalculatorItemUiModel.copy(
                    multiply = inputMultiply(digit)
                )
            }

            CalculatorButtonUiEvent.Division -> {
                currentCalculatorItemUiModel.copy(
                    division = inputDivision(digit)
                )
            }

            CalculatorButtonUiEvent.Subtract -> {
                currentCalculatorItemUiModel.copy(
                    subtract = inputSubtract(digit)
                )
            }

            else -> {
                currentCalculatorItemUiModel.copy(
                    value = currentCalculatorItemUiModel.value * 10 + digit
                )
            }
        }
    }

    private fun inputMultiply(digit: Int): Float {
        val currentMultiply: Float = currentCalculatorItemUiModel.multiply

        val isDecimalInput: Boolean = currentCalculatorItemUiModel.buttonsHistory.lastOrNull() ==
                CalculatorButtonUiEvent.Decimal

        return if (isDecimalInput) {
            val newMultiplyString: String = if (currentMultiply % 1f == 0f) {
                "${currentMultiply.toInt()}.$digit"
            } else {
                "$currentMultiply$digit"
            }
            newMultiplyString.toFloat()
        } else {
            if (currentMultiply == 0f) {
                digit.toFloat()
            } else {
                val integerPart: Int = currentMultiply.toInt()
                (integerPart * 10 + digit).toFloat()
            }
        }
    }

    private fun inputDivision(digit: Int): Float {
        val current = currentCalculatorItemUiModel.division
        val isDecimalInput = currentCalculatorItemUiModel.buttonsHistory.lastOrNull() ==
                CalculatorButtonUiEvent.Decimal

        return if (isDecimalInput) {
            // Agregar al decimal
            // Tomamos la parte decimal actual y sumamos el nuevo dígito
            val decimalPart: Int = ((current * 10).toInt() % 10) * 10 + digit
            val integerPart: Int = current.toInt()
            integerPart + decimalPart / 100f
        } else {
            // Agregar al entero
            if (current == 0f) {
                digit.toFloat()
            } else {
                val integerPart: Int = current.toInt()
                (integerPart * 10 + digit).toFloat()
            }
        }
    }

    private fun inputSubtract(digit: Int): Long {
        val current = currentCalculatorItemUiModel.subtract   // entero en centavos

        // 1) Caso: aún no tiene valor -> primer dígito entero
        if (current == 0L) {
            // Ej: value = 3 -> 300 => "3.00"
            return digit * 100L
        }

        // Detectar decimales
        val hasDecimals = current % 100 != 0L      // tiene 1 o 2 decimales
        val hasTwoDecimals = current % 10 != 0L    // tiene exactamente 2 decimales

        return when {
            // 2) Tiene 1 decimal -> agregar segundo decimal
            hasDecimals && !hasTwoDecimals -> {
                current * 10 + digit
            }

            // 3) Tiene 2 decimales -> no se permite más
            hasDecimals && hasTwoDecimals -> {
                current // ignorar input
            }

            // 4) No tiene decimales -> agregar dígito a parte entera
            else -> {
                val intPart = current / 100
                (intPart * 10 + digit) * 100
            }
        }
    }
}