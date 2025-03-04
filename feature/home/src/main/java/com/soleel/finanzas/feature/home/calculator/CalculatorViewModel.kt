package com.soleel.finanzas.feature.home.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.soleel.finanzas.core.ui.R

data class ItemUi(
    val value: Int = 0,
    val valueError: Int? = null,

    val quantity: Float = 1f,
    val quantityError: Int? = null,

    val subtract: Int = 0,
    val subtractError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val isMultiply: Boolean = false,
    val isSubtract: Boolean = false,
    val isDecimal: Boolean = false,
)

sealed class CalculatorButtonEventUi {
    data class Number(val value: Int) : CalculatorButtonEventUi()
    data object Clear : CalculatorButtonEventUi()
    data object Multiply : CalculatorButtonEventUi()
    data object Subtract : CalculatorButtonEventUi()
    data object Add : CalculatorButtonEventUi()
    data object Delete : CalculatorButtonEventUi()
    data object Decimal : CalculatorButtonEventUi() // TODO: Convertir a SPECIAL -> Add - Catalog, Multiply - Decimal, Subtract - Percentage/Currency
}

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    private var _currentItemUi: ItemUi by mutableStateOf(ItemUi())
    val currentItemUi: ItemUi get() = _currentItemUi

    private var _itemsInCartUi: List<ItemUi> by mutableStateOf(emptyList())
    val itemsInCartUi: List<ItemUi> get() = _itemsInCartUi

    fun onNameChanged(name: String) {
        _currentItemUi = currentItemUi.copy(name = name)
    }

    fun onButtonCalculatorEvent(event: CalculatorButtonEventUi) {
        when (event) {
            is CalculatorButtonEventUi.Number -> {
                // TODO: Que pasa con el 0??

                _currentItemUi = if (currentItemUi.isMultiply) {
                    if (currentItemUi.isDecimal) {
                        val quantityIntPart = currentItemUi.quantity.toInt()
                        val quantityDecimalPart: Float =
                            currentItemUi.quantity - currentItemUi.quantity.toInt()
                        val quantityDecimalPartToInt: Int =
                            quantityDecimalPart.toString().substringAfter(".").toInt()
                        val decimalResult: Int = (quantityDecimalPartToInt * 10) + event.value

                        currentItemUi.copy(quantity = "$quantityIntPart.$decimalResult".toFloat())
                    } else {
                        currentItemUi.copy(quantity = currentItemUi.quantity + event.value)
                    }
                } else {
                    currentItemUi.copy(value = currentItemUi.value * 10 + event.value)
                }
            }

            is CalculatorButtonEventUi.Clear -> {
                if (currentItemUi.value > 0) {
                    _currentItemUi = ItemUi()
                }
            }

            is CalculatorButtonEventUi.Multiply -> {
                // 1. Cambiar el foco del TextField en la vista al de cantidad
                // 2. Mostrar icono de multiplicacion entre QuantityTextField y ValueTextField
                // 3. Cambiar funcionamiento de los demas operadores
                if (currentItemUi.value > 0) {
                    _currentItemUi = currentItemUi.copy(isMultiply = true)
                }
            }

            is CalculatorButtonEventUi.Subtract -> {
                if (currentItemUi.value > 0) {
                    _currentItemUi = currentItemUi.copy(isSubtract = true)
                }
            }

            is CalculatorButtonEventUi.Add -> {
                if (currentItemUi.name.isBlank()) {
                    _currentItemUi = currentItemUi.copy(name = "item ${itemsInCartUi.size + 1}")
                }

                _itemsInCartUi = itemsInCartUi + currentItemUi
                _currentItemUi = ItemUi()
            }

            is CalculatorButtonEventUi.Delete -> {
                if (currentItemUi.isMultiply) {

                }

                if (currentItemUi.isDecimal) {

                }

                _currentItemUi = currentItemUi.copy(
                    value = if (currentItemUi.value > 9) { // Tiene decenas
                        currentItemUi.value / 10 // Se divide por 10, descartando el decimal
                    } else {
                        0 // Es un solo digito, se anula a 0
                    }
                )
            }

            is CalculatorButtonEventUi.Decimal -> {
                // Depende de la moneda, actualmente solo se considera la moneda chilena
//                if (currentCurrency) {
                _currentItemUi = currentItemUi.copy(
                    isDecimal = true
                )
//                }
            }
        }
    }

    fun addTransaction() {

    }
}