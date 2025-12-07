package com.soleel.finanzas.data.preferences.app


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soleel.finanzas.core.model.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ImplementationAppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IAppPreferences {

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val CONFIGURATION_KEY = stringPreferencesKey("configuration")
    }

    override suspend fun saveAuthToken(authToken: String) {
        dataStore.edit(transform = { preferences -> preferences[AUTH_TOKEN_KEY] = authToken })
    }

    override fun getAuthToken(): Flow<String?> {
        return dataStore.data
            .catch(action = { emit(emptyPreferences()) })
            .map(transform = { preferences -> preferences[AUTH_TOKEN_KEY] })
    }

    override suspend fun removeAuthToken() {
        dataStore.edit(transform = { preferences -> preferences.remove(AUTH_TOKEN_KEY) })
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun saveConfiguration(configuration: Configuration) {
        val json = Json.encodeToString(Configuration.serializer(), configuration)
        dataStore.edit(transform = { preferences -> preferences[CONFIGURATION_KEY] = json })
    }

    override fun getConfiguration(): Flow<Configuration?> {
        return dataStore.data.map(transform = { prefs ->
                prefs[CONFIGURATION_KEY]?.let(block = {
                    runCatching(block = {
                        Json.decodeFromString(Configuration.serializer(), it)
                    }).getOrNull()
                })
            }
        )
    }

    override suspend fun removeConfiguration() {
        dataStore.edit(transform = { preferences -> preferences.remove(CONFIGURATION_KEY) })
    }
}