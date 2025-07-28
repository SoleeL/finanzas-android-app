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
import com.soleel.finanzas.core.model.AccountWithExpensesInfo
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Item
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.domain.account.IGetAccountsWithExpensesInfoCurrentMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
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

sealed interface AccountsUiState {
    data class Success(val accountsWithInfo: List<AccountWithExpensesInfo>) : AccountsUiState
    data object Error : AccountsUiState
    data object Loading : AccountsUiState
}

sealed class AccountsUiEvent {
    data object Retry : AccountsUiEvent()
}

@HiltViewModel
open class CreateExpenseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAccountsUseCase: IGetAccountsWithExpensesInfoCurrentMonthUseCase,
    private val retryableFlowTrigger: RetryableFlowTrigger,
) : ViewModel() {

    private val items: List<Item> = savedStateHandle.get<List<Item>>("items") ?: emptyList()

    private fun getTotal(): Float {
        return items.sumOf(selector = { it.value.toDouble() }).toFloat()
    }

    // Privado y con nombre sin el UiState y con Flow
    private val _accountsUiStateFlow: Flow<AccountsUiState> =
        retryableFlowTrigger.retryableFlow(flowProvider = { getAccountsFlow() })

    private fun getAccountsFlow(): Flow<AccountsUiState> {
        return getAccountsUseCase()
            .asResult()
            .map(transform = { this.getAccountsData(it) })
    }

    private fun getAccountsData(accountsUiStateResult: Result<List<AccountWithExpensesInfo>>): AccountsUiState {
        return when (accountsUiStateResult) {
            is Result.Loading -> AccountsUiState.Loading
            is Result.Success -> AccountsUiState.Success(accountsWithInfo = accountsUiStateResult.data)
            else -> AccountsUiState.Error
        }
    }

    // Publico y con UiState y con Flow
    val accountsUiStateFlow: StateFlow<AccountsUiState> =
        _accountsUiStateFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = AccountsUiState.Loading
            )

    fun onAccountsUiEvent(event: AccountsUiEvent) {
        when (event) {
            is AccountsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
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
}