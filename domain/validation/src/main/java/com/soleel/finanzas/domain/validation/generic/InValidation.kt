package com.soleel.finanzas.domain.validation.generic


interface InValidation<In, Out> {
    fun execute(input: In): Out
}