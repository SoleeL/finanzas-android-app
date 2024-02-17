package com.soleel.common.retryflow.di

import com.soleel.common.retryflow.RetryableFlowTrigger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetryModule {

    @Provides
    @Singleton
    fun provideRetryableFlowTrigger(): RetryableFlowTrigger {
        return RetryableFlowTrigger()
    }

}
