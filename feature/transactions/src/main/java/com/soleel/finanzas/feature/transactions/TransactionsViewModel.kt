package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.core.model.TransactionsSum
import com.soleel.finanzas.domain.transactions.GetAllTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetAnnuallyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetDailyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetMonthlyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetWeeklyTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface TransactionsGroupUiState {
    data class Success(
        val transactionsGroup: List<TransactionsGroup>
    ) : TransactionsGroupUiState

    data object Error : TransactionsGroupUiState
    data object Loading : TransactionsGroupUiState
}

sealed interface TransactionsSumUiState {
    data class Success(
        val transactionsSum: List<TransactionsSum>
    ) : TransactionsSumUiState

    data object Error : TransactionsSumUiState
    data object Loading : TransactionsSumUiState
}

sealed class TransactionsUiEvent {
    data object Retry : TransactionsUiEvent()
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getDailyTransactionsUseCase: GetDailyTransactionsUseCase,
    private val getWeeklyTransactionsUseCase: GetWeeklyTransactionsUseCase,
    private val getMonthlyTransactionsUseCase: GetMonthlyTransactionsUseCase,
    private val getAnnuallyTransactionsUseCase: GetAnnuallyTransactionsUseCase,

    private val retryableFlowTrigger: RetryableFlowTrigger,
) : ViewModel() {

    private val _allTransactionsUiState: Flow<TransactionsGroupUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowAllTransactions() })

    private fun getFlowAllTransactions(): Flow<TransactionsGroupUiState> {
        return getAllTransactionsUseCase()
            .asResult()
            .map(transform = { this.getTransactionsGroupData(it) })
    }

    private fun getTransactionsGroupData(transactionsGroup: Result<List<TransactionsGroup>>): TransactionsGroupUiState {
        return when (transactionsGroup) {
            is Result.Loading -> TransactionsGroupUiState.Loading
            is Result.Success -> TransactionsGroupUiState.Success(transactionsGroup = transactionsGroup.data)
            else -> TransactionsGroupUiState.Error
        }
    }

    val allTransactionsUiState: StateFlow<TransactionsGroupUiState> = _allTransactionsUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsGroupUiState.Loading
        )

    private val _dailyTransactionsUiState: Flow<TransactionsSumUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowDailyTransactionsSum() })

    private fun getFlowDailyTransactionsSum(): Flow<TransactionsSumUiState> {
        return getDailyTransactionsUseCase()
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    val dailyTransactionsUiState: StateFlow<TransactionsSumUiState> =
        _dailyTransactionsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsSumUiState.Loading
        )

    private val _weeklyTransactionsUiState: Flow<TransactionsSumUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowWeeklyTransactions() })

    private fun getFlowWeeklyTransactions(): Flow<TransactionsSumUiState> {
        return getWeeklyTransactionsUseCase()
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    val weeklyTransactionsUiState: StateFlow<TransactionsSumUiState> =
        _weeklyTransactionsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsSumUiState.Loading
        )

    private val _monthlyTransactionsUiState: Flow<TransactionsSumUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowMonthlyTransactions() })

    private fun getFlowMonthlyTransactions(): Flow<TransactionsSumUiState> {
        return getMonthlyTransactionsUseCase()
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    val monthlyTransactionsUiState: StateFlow<TransactionsSumUiState> =
        _monthlyTransactionsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsSumUiState.Loading
        )

    private val _annuallyTransactionsUiState: Flow<TransactionsSumUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowAnnuallyTransactions() })

    private fun getFlowAnnuallyTransactions(): Flow<TransactionsSumUiState> {
        return getAnnuallyTransactionsUseCase()
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    val annuallyTransactionsUiState: StateFlow<TransactionsSumUiState> =
        _annuallyTransactionsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsSumUiState.Loading
        )

    private fun getTransactionsSumData(transactionsSum: Result<List<TransactionsSum>>): TransactionsSumUiState {
        return when (transactionsSum) {
            is Result.Loading -> TransactionsSumUiState.Loading
            is Result.Success -> TransactionsSumUiState.Success(transactionsSum = transactionsSum.data)
            else -> TransactionsSumUiState.Error
        }
    }

    fun onTransactionsUiEvent(event: TransactionsUiEvent) {
        when (event) {
            is TransactionsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

}
