package com.soleel.finanzas.feature.createexpense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

data class CreateExpenseUiModel(
    val amount: Float = 0f,
    val size: Float = 0f,

    val expenseType: ExpenseTypeEnum? = null,
    val expenseDate: LocalDateTime = LocalDateTime.now(),
    val name: String = "",
    val accountType: AccountTypeEnum? = null,
    val account: Account? = null,
    val instalments: Int = 0
)

sealed class CreateExpenseUiEvent {
    data object ExpenseTypeSelected : CreateExpenseUiEvent()
    data object ExpenseDateSelected : CreateExpenseUiEvent()
    data object ExpenseNameEntered : CreateExpenseUiEvent()
    data object AccountTypeSelected : CreateExpenseUiEvent()
    data object AccountSelected : CreateExpenseUiEvent()
    data object InstalmentsSelected : CreateExpenseUiEvent()
}

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val items: List<Item> = savedStateHandle.get<List<Item>>("items") ?: emptyList()

    private val _createExpenseUiModel: CreateExpenseUiModel by mutableStateOf(CreateExpenseUiModel())
    val createExpenseUiModel: CreateExpenseUiModel get() = _createExpenseUiModel
}


//@HiltViewModel
//class CreateExpenseViewModel @Inject constructor(
//    val items: List<Item>
//) : ViewModel() { }
//
////"Gasté $[monto] en [nombre del gasto] ([tipo de gasto]) [fecha] [en X artículos] usando mi [nombre de cuenta] [tipo de cuenta][, en X cuotas]."
//
//    private val _createExpenseUiModel: CreateExpenseUiModel by mutableStateOf(CreateExpenseUiModel())
//    val createExpenseUiModel: CreateExpenseUiModel get() = _createExpenseUiModel
//
//}