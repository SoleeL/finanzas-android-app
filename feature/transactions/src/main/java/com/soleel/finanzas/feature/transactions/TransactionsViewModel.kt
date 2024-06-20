package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.data.transaction.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface TransactionsUiState {
    data class Success(
        val allTransactions: List<Transaction>,
        val paymentAccounts: List<PaymentAccount>
    ) : TransactionsUiState
    data object Error : TransactionsUiState
    data object Loading : TransactionsUiState
}

sealed class TransactionsUiEvent {
    data object Retry : TransactionsUiEvent()
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    private val _transactionsUiState: Flow<TransactionsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = {
            getFlowTransactionsUiState(
                transactionRepository = transactionRepository,
                paymentAccountRepository = paymentAccountRepository
            )
        })

    val transactionsUiState: StateFlow<TransactionsUiState> = _transactionsUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = TransactionsUiState.Loading
    )

    private fun getFlowTransactionsUiState(
        transactionRepository: ITransactionLocalDataSource,
        paymentAccountRepository: IPaymentAccountLocalDataSource
    ): Flow<TransactionsUiState> {
        return combine(
            flow = transactionRepository.getTransactions().asResult(),
            flow2 = paymentAccountRepository.getPaymentAccounts().asResult(),
            transform = this::getData
        )
    }

    private fun getData(
        transactionResult: Result<List<Transaction>>,
        paymentAccountResult: Result<List<PaymentAccount>>
    ): TransactionsUiState {
        return when {
            transactionResult is Result.Loading || paymentAccountResult is Result.Loading -> TransactionsUiState.Loading
            transactionResult is Result.Error || paymentAccountResult is Result.Error -> TransactionsUiState.Error
            transactionResult is Result.Success && paymentAccountResult is Result.Success -> TransactionsUiState.Success(
                allTransactions = transactionResult.data,
                paymentAccounts = paymentAccountResult.data
            )
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
