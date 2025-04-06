package com.soleel.finanzas.data.preferences.app

import com.soleel.finanzas.core.model.Configuration
import kotlinx.coroutines.flow.Flow


interface IAppPreferences {
    suspend fun saveAuthToken(authToken: String)
    fun getAuthToken(): Flow<String?>
    suspend fun removeAuthToken()

    suspend fun saveConfiguration(configuration: Configuration)
    fun getConfiguration(): Flow<Configuration?>
    suspend fun removeConfiguration()
}