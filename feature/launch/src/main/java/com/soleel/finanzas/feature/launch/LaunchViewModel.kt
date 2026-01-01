package com.soleel.finanzas.feature.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.UiState
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.Configuration
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val appPreferences: IAppPreferences = MockAppPreferences(),
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {
    private val _destinationUiState: Flow<UiState<Any>> = retryableFlowTrigger
        .retryableFlow<UiState<Any>>(flowProvider = { getFlowMain() })

    val destinationUiState: StateFlow<UiState<Any>> = _destinationUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = UiState.Loading
        )

    private fun getFlowMain(): Flow<UiState<Any>> {
        // flow(block = { -> Se infiere el tipo a emitir por el catch segun el primer emit
        return flow<UiState<Any>>(
            block = {
                val config: Configuration? = appPreferences.getConfiguration().firstOrNull()

                val destination = when {
                    config == null -> ConfigurationGraph
                    else -> HomeGraph
                }

                emit(UiState.Success<Any>(destination))
            }
        ).catch(
            action = { throwable ->
                emit(UiState.Failure(throwable))
            }
        )
    }
}