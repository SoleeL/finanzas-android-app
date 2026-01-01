package com.soleel.finanzas.core.common

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>

    data class Failure(val exception: Throwable) : UiState<Nothing> {
        val code: String
            get() = when (exception) {
                is CustomException -> exception.code   // ya es un String
                else -> exception::class.simpleName ?: "unknown"
            }

        val message: String
            get() = exception.message ?: "unknown"
    }
}