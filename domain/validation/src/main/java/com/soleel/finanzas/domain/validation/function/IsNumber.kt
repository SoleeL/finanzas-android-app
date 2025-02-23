package com.soleel.finanzas.domain.validation.function


fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}