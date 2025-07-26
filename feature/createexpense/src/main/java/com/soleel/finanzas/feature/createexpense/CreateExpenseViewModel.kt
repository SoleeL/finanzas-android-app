package com.soleel.finanzas.feature.createexpense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    val accountType: AccountTypeEnum? = null,
    val account: Account? = null,
    val instalments: Int? = null,
    val expenseDate: LocalDateTime? = null,
    val name: String? = null
)

sealed class CreateExpenseUiEvent {
    data class ExpenseTypeSelected(val expenseType: ExpenseTypeEnum) : CreateExpenseUiEvent()
    data class AccountTypeSelected(val accountType: AccountTypeEnum) : CreateExpenseUiEvent()
    data class AccountSelected(val account: Account) : CreateExpenseUiEvent()
    data class InstalmentsSelected(val instalments: Int) : CreateExpenseUiEvent()
    data class ExpenseDateSelected(val expenseDate: LocalDateTime) : CreateExpenseUiEvent()
    data class ExpenseName(val name: String) : CreateExpenseUiEvent()
}

@HiltViewModel
open class CreateExpenseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val items: List<Item> = savedStateHandle.get<List<Item>>("items") ?: emptyList()

    private var _createExpenseUiModel: CreateExpenseUiModel by mutableStateOf(CreateExpenseUiModel())
    val createExpenseUiModel: CreateExpenseUiModel get() = _createExpenseUiModel


    private val _expensesButtonsUi: List<List<ExpenseTypeEnum>> by mutableStateOf(
        listOf(
            listOf(ExpenseTypeEnum.SERVICES, ExpenseTypeEnum.MARKET, ExpenseTypeEnum.ACQUISITION),
            listOf(ExpenseTypeEnum.LEASURE, ExpenseTypeEnum.GIFT, ExpenseTypeEnum.TRANSFER),
            listOf(ExpenseTypeEnum.OTHER)
        )
    )

    val expensesButtonUi: List<List<ExpenseTypeEnum>> get() = _expensesButtonsUi

    fun onCreateExpenseUiEvent(event: CreateExpenseUiEvent) {
        when (event) {
            is CreateExpenseUiEvent.ExpenseTypeSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(expenseType = event.expenseType)
            }
            is CreateExpenseUiEvent.AccountTypeSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(accountType = event.accountType)
            }
            is CreateExpenseUiEvent.AccountSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(account = event.account)
            }
            is CreateExpenseUiEvent.InstalmentsSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(instalments = event.instalments)
            }
            is CreateExpenseUiEvent.ExpenseDateSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(expenseDate = event.expenseDate)
            }
            is CreateExpenseUiEvent.ExpenseName -> {
                _createExpenseUiModel = createExpenseUiModel.copy(name = event.name)
            }
        }
    }
}