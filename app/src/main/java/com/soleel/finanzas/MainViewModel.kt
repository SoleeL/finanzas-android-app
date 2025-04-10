package com.soleel.finanzas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.login.LoginGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MainUiState {
    data class Success(val startDestination: Any) : MainUiState
    data object Error : MainUiState
    data object Loading : MainUiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferences: IAppPreferences = MockAppPreferences(),

    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {
    private val _mainUiState: Flow<MainUiState> = retryableFlowTrigger
        .retryableFlow<MainUiState> (flowProvider = { getFlowMain() })
    val mainUiState: StateFlow<MainUiState> = _mainUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = MainUiState.Loading
        )

    private fun getFlowMain(): Flow<MainUiState> {
        // flow(block = { -> Se infiere el tipo a emitir por el catch segun el primer emit
        return flow<MainUiState>(block = {
            val authToken = appPreferences.getAuthToken().firstOrNull()
            val config = appPreferences.getConfiguration().firstOrNull()

            val destination = when {
                authToken == null -> LoginGraph
                config == null -> ConfigurationGraph
                else -> HomeGraph
            }

            emit(MainUiState.Success(destination))
        }).catch(action = { emit(MainUiState.Error) })
    }
}
