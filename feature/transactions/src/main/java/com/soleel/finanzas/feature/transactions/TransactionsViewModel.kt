package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.domain.transactions.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface TransactionsUiState {
    data class Success(
        val allTransactionsWithAccount: List<TransactionWithAccount>
    ) : TransactionsUiState

    data object Error : TransactionsUiState
    data object Loading : TransactionsUiState
}

sealed class TransactionsUiEvent {
    data object Retry : TransactionsUiEvent()
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getAllTransactionsWithAccountUseCase: GetAllTransactionsUseCase,
    private val retryableFlowTrigger: RetryableFlowTrigger,
) : ViewModel() {

    private val _transactionsUiState: Flow<TransactionsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { getFlowAllTransactionsWithAccount() })

    val transactionsUiState: StateFlow<TransactionsUiState> = _transactionsUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = TransactionsUiState.Loading
    )

    private fun getFlowAllTransactionsWithAccount(): Flow<TransactionsUiState> {
        return getAllTransactionsWithAccountUseCase().asResult().map(transform = { this.getData(it) })
    }

    private fun getData(allTransactionsWithAccount: Result<List<TransactionWithAccount>>): TransactionsUiState {
        return when (allTransactionsWithAccount) {
            is Result.Loading -> TransactionsUiState.Loading
            is Result.Success -> TransactionsUiState.Success(allTransactionsWithAccount = allTransactionsWithAccount.data)
            else -> TransactionsUiState.Error
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
