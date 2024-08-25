package com.soleel.finanzas.feature.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface AccountsUiState {
    data class Success(val accounts: List<Account>) : AccountsUiState
    data object Error : AccountsUiState
    data object Loading : AccountsUiState
}

sealed class AccountsUiEvent {
    data object Retry : AccountsUiEvent()
}

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger,
    val singleEventManager: SingleEventManager
) : ViewModel() {

    private val _accountsUiState: Flow<AccountsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { accountUiState(accountRepository = accountRepository) })

    private fun accountUiState(
        accountRepository: IAccountLocalDataSource,
    ): Flow<AccountsUiState> {
        return accountRepository.getAccountsWithTransactionInfo()
            .asResult()
            .map(transform = this::getData)
    }

    private fun getData(
        itemsAccount: Result<List<Account>>
    ): AccountsUiState {
        return when (itemsAccount) {
            is Result.Success -> AccountsUiState.Success(itemsAccount.data)
            is Result.Error -> AccountsUiState.Error
            is Result.Loading -> AccountsUiState.Loading
        }
    }

    val accountsUiState: StateFlow<AccountsUiState> = _accountsUiState
        .onStart(action = { delay(500) })
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
}