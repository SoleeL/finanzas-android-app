package com.soleel.finanzas.data.preferences.app

import com.soleel.finanzas.core.model.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockAppPreferences : IAppPreferences {

    private val authTokenFlow = MutableStateFlow<String?>(null)
    private val configurationFlow = MutableStateFlow<Configuration?>(null)

    override suspend fun saveAuthToken(authToken: String) {
        authTokenFlow.value = authToken
    }

    override fun getAuthToken(): Flow<String?> = authTokenFlow

    override suspend fun removeAuthToken() {
        authTokenFlow.value = null
    }

    override suspend fun saveConfiguration(configuration: Configuration) {
        configurationFlow.value = configuration
    }

    override fun getConfiguration(): Flow<Configuration?> = configurationFlow

    override suspend fun removeConfiguration() {
        configurationFlow.value = null
    }
}