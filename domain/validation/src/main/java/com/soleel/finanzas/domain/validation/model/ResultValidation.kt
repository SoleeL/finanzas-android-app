package com.soleel.finanzas.domain.validation.model


data class ResultValidation(
    val successful: Boolean,
    val errorMessage: Int? = null
)