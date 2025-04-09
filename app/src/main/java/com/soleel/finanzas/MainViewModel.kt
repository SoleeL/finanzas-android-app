package com.soleel.finanzas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.login.LoginGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferences: IAppPreferences = MockAppPreferences()
) : ViewModel() {
    private val _startDestination = MutableStateFlow<Any>(HomeGraph)
    val startDestination: StateFlow<Any> = _startDestination

    init {
        viewModelScope.launch {
            val authToken = appPreferences.getAuthToken().firstOrNull()
            val config = appPreferences.getConfiguration().firstOrNull()

            val destination = when {
                authToken == null -> LoginGraph
                config == null -> ConfigurationGraph
                else -> HomeGraph
            }
            _startDestination.value = destination
        }
    }
}
