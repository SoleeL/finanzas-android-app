package com.soleel.finanzas.data.preferences


import com.soleel.finanzas.core.model.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import javax.inject.Inject

const val DATASTORE_NAME = "app_preferences"
const val AUTH_TOKEN = "auth_token"
const val CONFIGURATION = "configuration"

class AppPreferences @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend fun saveAuthToken(authToken: String) {
        this.preferenceManager.saveStringPreference(AUTH_TOKEN, authToken)
    }

    fun getAuthToken() : Flow<String?> {
        return this.preferenceManager.getStringPreference(AUTH_TOKEN)
    }

    suspend fun removeAuthToken(){
        this.preferenceManager.removeStringPreference(AUTH_TOKEN)
    }


    @OptIn(InternalSerializationApi::class)
    suspend fun saveConfiguration(configuration: Configuration) {
        this.preferenceManager.saveSerializablePreference(CONFIGURATION, configuration, Configuration::class.serializer())
    }

    @OptIn(InternalSerializationApi::class)
    fun getConfiguration() : Flow<Configuration?> {
        return this.preferenceManager.getSerializablePreference(CONFIGURATION, Configuration::class.serializer())
    }

    suspend fun removeConfiguration() {
        this.preferenceManager.removeSerializablePreference(CONFIGURATION)
    }
}