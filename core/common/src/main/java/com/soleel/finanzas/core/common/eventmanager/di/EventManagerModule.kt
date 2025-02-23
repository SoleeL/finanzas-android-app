package com.soleel.finanzas.core.common.eventmanager.di

import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventManagerModule {

    @Provides
    @Singleton
    fun provideSingleEventManager(): SingleEventManager {
        return SingleEventManager()
    }
}