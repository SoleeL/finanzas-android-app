package com.soleel.finanzas.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.soleel.finanzas.data.preferences.app.IAppPreferences
import com.soleel.finanzas.data.preferences.app.ImplementationAppPreferences
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


const val DATASTORE_NAME = "preferences"

@Module
@InstallIn(SingletonComponent::class)
object ImplementationPreferencesModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
        )
    }

    @Singleton
    @Provides
    fun provideAppPreferences(dataStore: DataStore<Preferences>): IAppPreferences {
        if (BuildConfig.DEMO) {
            return MockAppPreferences()
        }

        return ImplementationAppPreferences(dataStore)
    }
}