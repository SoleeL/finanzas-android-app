package com.soleel.finanzas.feature.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.UiState
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.Configuration
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class LaunchUiNavigation {
    data object ToConfiguration: LaunchUiNavigation()
    data object ToCreateAccount: LaunchUiNavigation()
    data object ToHome: LaunchUiNavigation()
}

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val appPreferences: IAppPreferences = MockAppPreferences(),
    private val accountRepository: IAccountRepository,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    private val _destinationUiState: Flow<UiState<LaunchUiNavigation>> = retryableFlowTrigger
        .retryableFlow<UiState<LaunchUiNavigation>>(flowProvider = { getFlowMain() })

    val destinationUiState: StateFlow<UiState<LaunchUiNavigation>> = _destinationUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = UiState.Loading
        )

    private fun getFlowMain(): Flow<UiState<LaunchUiNavigation>> {
        // flow(block = { -> Se infiere el tipo a emitir por el catch segun el primer emit
        return flow<UiState<LaunchUiNavigation>>(
            block = {

                delay(1_000) // Simula request

                val config: Configuration? = appPreferences.getConfiguration().firstOrNull()

                if (config == null) {
                    emit(UiState.Success<LaunchUiNavigation>(LaunchUiNavigation.ToConfiguration))
                    return@flow
                }

                val accountsCount: Int = accountRepository.getAccountsNotDeletedCount()

                if (accountsCount == 0) {
                    emit(UiState.Success<LaunchUiNavigation>(LaunchUiNavigation.ToCreateAccount))
                    return@flow
                }

                //                throw RemoteException("error de prueba")

                emit(UiState.Success<LaunchUiNavigation>(LaunchUiNavigation.ToHome))
            }
        ).catch(
            action = { throwable ->
                emit(UiState.Failure(throwable))
            }
        )
    }

    fun retry() {
        retryableFlowTrigger.retry()
    }

}