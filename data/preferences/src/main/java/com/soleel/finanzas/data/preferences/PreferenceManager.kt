package com.soleel.finanzas.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveStringPreference(key: String, value: String) {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences[dataStoreKey] = value })
    }

    fun getStringPreference(key: String): Flow<String?> {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        return dataStore.data
            .catch(action = { emit(emptyPreferences()) })
            .map(transform = { preferences -> preferences[dataStoreKey] })
    }

    suspend fun removeStringPreference(key: String) {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences.remove(dataStoreKey) })
    }


    suspend fun saveIntPreference(key: String, value: Int) {
        val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences[dataStoreKey] = value })
    }

    fun getIntPreference(key: String, defaultValue: Int): Flow<Int> {
        val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
        return dataStore.data
            .catch(action = { emit(emptyPreferences()) })
            .map(transform = { preferences -> preferences[dataStoreKey] ?: defaultValue })
    }

    suspend fun removeIntPreference(key: String) {
        val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences.remove(dataStoreKey) })
    }


    suspend fun saveBooleanPreference(key: String, value: Boolean) {
        val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences[dataStoreKey] = value })
    }

    fun getBooleanPreference(key: String, defaultValue: Boolean): Flow<Boolean> {
        val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
        return dataStore.data
            .catch(action = { emit(emptyPreferences()) })
            .map(transform = { preferences -> preferences[dataStoreKey] ?: defaultValue })
    }

    suspend fun removeBooleanPreference(key: String) {
        val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences.remove(dataStoreKey) })
    }


    suspend fun <T> saveSerializablePreference(key: String, value: T, serializer: KSerializer<T>) {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val json = Json.encodeToString(serializer, value)
        dataStore.edit(transform = { preferences -> preferences[dataStoreKey] = json })
    }

    fun <T> getSerializablePreference(key: String, serializer: KSerializer<T>): Flow<T?> {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        return dataStore.data
            .catch(action = { emit(emptyPreferences()) })
            .map(
                transform = { preferences ->
                    preferences[dataStoreKey]?.let(
                        block = { json ->
                            try {
                                Json.decodeFromString(serializer, json)
                            } catch (e: Exception) {
                                null
                            }
                        }
                    )
                }
            )
    }

    suspend fun removeSerializablePreference(key: String) {
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        dataStore.edit(transform = { preferences -> preferences.remove(dataStoreKey) })
    }
}