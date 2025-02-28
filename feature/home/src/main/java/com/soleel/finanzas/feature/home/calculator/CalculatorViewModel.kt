package com.soleel.finanzas.feature.home.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ItemUi(
    val value: Int = 0,
    val valueError: Int? = null,

    val quantity: Float = 0f,
    val quantityError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val isMultiply: Boolean = false,
    val isDecimal: Boolean = false
)

sealed class CalculatorButton {
    data class Number(val value: Int) : CalculatorButton()
    data object Clear : CalculatorButton()
    data object Multiply : CalculatorButton()
    data object More : CalculatorButton()
    data object Delete : CalculatorButton()
    data object Decimal : CalculatorButton()
}

sealed class ItemEventUi {
    data class ValueChanged(val button: CalculatorButton) : ItemEventUi()
    data class QuantityChanged(val quantity: Float) : ItemEventUi()
    data class NameChanged(val name: String) : ItemEventUi()
//    data object Submit : ItemEventUi()
}

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    var currentItemUi: ItemUi by mutableStateOf(ItemUi())
    var itemsInCartUi: List<ItemUi> by mutableStateOf(emptyList())

    fun onItemEventUi(event: ItemEventUi) {
        when (event) {
            is ItemEventUi.ValueChanged -> {
                onButtonCalculatorClick(event.button)
//                validateValue()
            }

            is ItemEventUi.QuantityChanged -> {
                currentItemUi = currentItemUi.copy(quantity = event.quantity)
//                validateQuantity()
            }

            is ItemEventUi.NameChanged -> {
                currentItemUi = currentItemUi.copy(name = event.name)
//                validateName()
            }
        }
    }

    private fun onButtonCalculatorClick(button: CalculatorButton) {
        when (button) {
            is CalculatorButton.Number -> {
                // TODO: Que pasa con el 0??
                
                currentItemUi = if (currentItemUi.isMultiply) {
                    if (currentItemUi.isDecimal) {
                        val quantityIntPart = currentItemUi.quantity.toInt()
                        val quantityDecimalPart: Float = currentItemUi.quantity - currentItemUi.quantity.toInt()
                        val quantityDecimalPartToInt: Int = quantityDecimalPart.toString().substringAfter(".").toInt()
                        val decimalResult: Int = (quantityDecimalPartToInt * 10) + button.value

                        currentItemUi.copy(quantity = "$quantityIntPart.$decimalResult".toFloat())
                    } else {
                        currentItemUi.copy(quantity = currentItemUi.quantity + button.value)
                    }
                } else {
                    currentItemUi.copy(value = currentItemUi.value * 10 + button.value)
                }
            }

            is CalculatorButton.Clear -> {
                if (currentItemUi.value > 0) {
                    currentItemUi = ItemUi()
                }
            }

            is CalculatorButton.Multiply -> {
                // 1. Cambiar el foco del TextField en la vista al de cantidad
                // 2. Mostrar icono de multiplicacion entre QuantityTextField y ValueTextField
                // 3. Cambiar funcionamiento de los demas operadores
            }

            is CalculatorButton.More -> {
                itemsInCartUi = itemsInCartUi + currentItemUi
                currentItemUi = ItemUi()
            }

            is CalculatorButton.Delete -> {
                if (currentItemUi.isMultiply) {

                }

                if (currentItemUi.isDecimal) {

                }

                currentItemUi = currentItemUi.copy(
                    value = if (currentItemUi.value > 9) { // Tiene decenas
                        currentItemUi.value / 10 // Se divide por 10, descartando el decimal
                    } else {
                        0 // Es un solo digito, se anula a 0
                    }
                )
            }

            is CalculatorButton.Decimal -> {
                // Depende de la moneda, actualmente solo se considera la moneda chilena
//                if (currentCurrency) {
                    currentItemUi = currentItemUi.copy(
                        quantity =
                    )
//                }
            }
        }
    }

    private fun addItemToCart() {

    }
}