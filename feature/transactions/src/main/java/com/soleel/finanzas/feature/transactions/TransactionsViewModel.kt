package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.SummaryTransactions
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.domain.transactions.GetAllTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetAnnuallyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetDailyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetMonthlyTransactionsUseCase
import com.soleel.finanzas.domain.transactions.GetWeeklyTransactionsUseCase
import com.soleel.finanzas.feature.transactions.navigation.TransactionsArgs
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
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

sealed interface SummaryTransactionsUiState {
    data class Success(
        val summaryTransactions: List<SummaryTransactions>
    ) : SummaryTransactionsUiState

    data object Error : SummaryTransactionsUiState
    data object Loading : SummaryTransactionsUiState
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
    private val summaryPeriod: TransactionsLevelDestination = transactionsArgs.summaryPeriod

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

    private val _summaryTransactionsUiState: Flow<SummaryTransactionsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowTransactionsSum() })

    private fun getFlowTransactionsSum(): Flow<SummaryTransactionsUiState> {
        return (when (summaryPeriod) {
            TransactionsLevelDestination.DAILY_TRANSACTIONS -> getDailyTransactionsUseCase()
            TransactionsLevelDestination.WEEKLY_TRANSACTIONS -> getWeeklyTransactionsUseCase()
            TransactionsLevelDestination.MONTHLY_TRANSACTIONS -> getMonthlyTransactionsUseCase()
            TransactionsLevelDestination.ANNUALLY_TRANSACTIONS -> getAnnuallyTransactionsUseCase()
            else -> flowOf(emptyList())
        })
            .asResult()
            .map(transform = { this.getTransactionsSumData(it) })
    }

    private fun getTransactionsSumData(summaryTransactions: Result<List<SummaryTransactions>>): SummaryTransactionsUiState {
        return when (summaryTransactions) {
            is Result.Loading -> SummaryTransactionsUiState.Loading
            is Result.Success -> SummaryTransactionsUiState.Success(summaryTransactions = summaryTransactions.data)
            else -> SummaryTransactionsUiState.Error
        }
    }

    val summaryTransactionsUiState: StateFlow<SummaryTransactionsUiState> =
        _summaryTransactionsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = SummaryTransactionsUiState.Loading
        )

    fun onTransactionsUiEvent(event: TransactionsUiEvent) {
        when (event) {
            is TransactionsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

}
