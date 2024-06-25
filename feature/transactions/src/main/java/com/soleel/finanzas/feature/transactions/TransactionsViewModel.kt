package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.domain.transactions.GetAllTransactionsWithAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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
    private val getAllTransactionsWithAccountUseCase: GetAllTransactionsWithAccountUseCase,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    private val _transactionsUiState: MutableStateFlow<TransactionsUiState> =
        MutableStateFlow(TransactionsUiState.Loading)
    val transactionsUiState: StateFlow<TransactionsUiState> = _transactionsUiState

    init {
        getAllTransactionsWithAccount()
    }
    
    private fun getAllTransactionsWithAccount() {
        viewModelScope.launch {
            _transactionsUiState.value = TransactionsUiState.Loading
            try {
                _transactionsUiState.value = TransactionsUiState.Success(
                    allTransactionsWithAccount = getAllTransactionsWithAccountUseCase()
                )
            } catch (e: Exception) {
                _transactionsUiState.value = TransactionsUiState.Error
            }
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
