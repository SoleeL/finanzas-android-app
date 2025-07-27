package com.soleel.finanzas.feature.createexpense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.AccountWithExpensesMonthInfo
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.domain.account.GetAccountWithExpensesMonthInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import javax.inject.Inject

data class CreateExpenseUiModel(
    val amount: Float,
    val size: Int,

    val expenseType: ExpenseTypeEnum? = null,
    val account: Account? = null,
    val instalments: Int? = null,
    val expenseDate: LocalDateTime? = null,
    val name: String? = null,
)

sealed class CreateExpenseUiEvent {
    data class ExpenseTypeSelected(val expenseType: ExpenseTypeEnum) : CreateExpenseUiEvent()
    data class AccountSelected(val account: Account) : CreateExpenseUiEvent()
    data class InstalmentsSelected(val instalments: Int) : CreateExpenseUiEvent()
    data class ExpenseDateSelected(val expenseDate: LocalDateTime) : CreateExpenseUiEvent()
    data class ExpenseName(val name: String) : CreateExpenseUiEvent()
}

sealed interface AccountWithExpensesMonthInfoUiState {
    data class Success(val accountsWithExpensesMonthInfo: List<AccountWithExpensesMonthInfo>) :
        AccountWithExpensesMonthInfoUiState

    data object Error : AccountWithExpensesMonthInfoUiState
    data object Loading : AccountWithExpensesMonthInfoUiState
}

@HiltViewModel
open class CreateExpenseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAccountWithExpensesMonthInfoUseCase: GetAccountWithExpensesMonthInfoUseCase,
    private val retryableFlowTrigger: RetryableFlowTrigger,
) : ViewModel() {

    private val items: List<Item> = savedStateHandle.get<List<Item>>("items") ?: emptyList()

    private fun getTotal(): Float {
        return items.sumOf(selector = { it.value.toDouble() }).toFloat()
    }

    private var _createExpenseUiModel: CreateExpenseUiModel by mutableStateOf(
        CreateExpenseUiModel(
            amount = getTotal(),
            size = items.size
        )
    )
    val createExpenseUiModel: CreateExpenseUiModel get() = _createExpenseUiModel

    fun onCreateExpenseUiEvent(event: CreateExpenseUiEvent) {
        when (event) {
            is CreateExpenseUiEvent.ExpenseTypeSelected -> {
                _createExpenseUiModel = createExpenseUiModel.copy(expenseType = event.expenseType)
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

    // Privado y con nombre sin el UiState y con Flow
    private val _accountWithExpensesMonthInfoFlow: Flow<AccountWithExpensesMonthInfoUiState> =
        retryableFlowTrigger.retryableFlow(flowProvider = { getFlowAccountWithExpensesMonthInfo() })

    private fun getFlowAccountWithExpensesMonthInfo(): Flow<AccountWithExpensesMonthInfoUiState> {
        return getAccountWithExpensesMonthInfoUseCase()
            .asResult()
            .map(transform = { this.getAccountWithExpensesMonthInfoData(it) })
    }

    private fun getAccountWithExpensesMonthInfoData(
        accountsWithExpensesInfoResult: Result<List<AccountWithExpensesMonthInfo>>,
    ): AccountWithExpensesMonthInfoUiState {
        return when (accountsWithExpensesInfoResult) {
            is Result.Loading -> AccountWithExpensesMonthInfoUiState.Loading
            is Result.Success -> AccountWithExpensesMonthInfoUiState.Success(
                accountsWithExpensesMonthInfo = accountsWithExpensesInfoResult.data
            )
            else -> AccountWithExpensesMonthInfoUiState.Error
        }
    }

    // Publico y con UiState y con Flow
    val accountWithExpensesMonthInfoUiUiStateFlow: StateFlow<AccountWithExpensesMonthInfoUiState> =
        _accountWithExpensesMonthInfoFlow
            .onStart(action = { delay(500) })
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = AccountWithExpensesMonthInfoUiState.Loading
            )
}