package com.soleel.finanzas.domain.validation.generic


interface InIsValidation<In, Is, Out> {
    fun execute(input: In, inputIs: Is): Out
}