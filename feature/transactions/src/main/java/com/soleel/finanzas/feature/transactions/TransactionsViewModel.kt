package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.core.model.TransactionsSummary
import com.soleel.finanzas.domain.transactions.GetAllTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetAnnuallyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetDailyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetMonthlyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetWeeklyTransactionsUseCase
import com.soleel.finanzas.feature.transactions.navigation.TransactionsArgs
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.delayFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface TransactionsGroupUiState {
    data class Success(
        val transactionsGroupList: List<TransactionsGroup>
    ) : TransactionsGroupUiState

    data object Error : TransactionsGroupUiState
    data object Loading : TransactionsGroupUiState
}

sealed interface TransactionsSummaryUiState {
    data class Success(
        val transactionsSummaryList: List<TransactionsSummary>
    ) : TransactionsSummaryUiState

    data object Error : TransactionsSummaryUiState
    data object Loading : TransactionsSummaryUiState
}

sealed class TransactionsUiEvent {
    data object Retry : TransactionsUiEvent()
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getDailyTransactionsUseCase: GetDailyTransactionsUseCase,
    private val getWeeklyTransactionsUseCase: GetWeeklyTransactionsUseCase,
    private val getMonthlyTransactionsUseCase: GetMonthlyTransactionsUseCase,
    private val getAnnuallyTransactionsUseCase: GetAnnuallyTransactionsUseCase,

    private val retryableFlowTrigger: RetryableFlowTrigger,
) : ViewModel() {

    private val transactionsArgs: TransactionsArgs = TransactionsArgs(savedStateHandle)
    val summaryPeriod: TransactionsLevelDestination = transactionsArgs.summaryPeriod

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
            is Result.Success -> TransactionsGroupUiState.Success(transactionsGroupList = transactionsGroup.data)
            else -> TransactionsGroupUiState.Error
        }
    }

    val allTransactionsUiState: StateFlow<TransactionsGroupUiState> = _allTransactionsUiState
        .onStart(action = { delay(500) })
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsGroupUiState.Loading
        )

    private val _transactionsSummaryUiState: Flow<TransactionsSummaryUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowTransactionsSum() })

    private fun getFlowTransactionsSum(): Flow<TransactionsSummaryUiState> {
        return (when (summaryPeriod) {
            TransactionsLevelDestination.DAILY -> getDailyTransactionsUseCase()
            TransactionsLevelDestination.WEEKLY -> getWeeklyTransactionsUseCase()
            TransactionsLevelDestination.MONTHLY -> getMonthlyTransactionsUseCase()
            TransactionsLevelDestination.ANNUALLY -> getAnnuallyTransactionsUseCase()
            else -> flowOf(emptyList())
        })
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    private fun getTransactionsSumData(summaryTransactions: Result<List<TransactionsSummary>>): TransactionsSummaryUiState {
        return when (summaryTransactions) {
            is Result.Loading -> TransactionsSummaryUiState.Loading
            is Result.Success -> TransactionsSummaryUiState.Success(transactionsSummaryList = summaryTransactions.data)
            else -> TransactionsSummaryUiState.Error
        }
    }

    val transactionsSummaryUiState: StateFlow<TransactionsSummaryUiState> = _transactionsSummaryUiState
        .onStart(action = { delay(500) })
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = TransactionsSummaryUiState.Loading
        )

    fun onTransactionsUiEvent(event: TransactionsUiEvent) {
        when (event) {
            is TransactionsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

}
