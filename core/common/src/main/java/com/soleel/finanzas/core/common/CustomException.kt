package com.soleel.finanzas.core.common

open class CustomException(
    val code: String,
    override val message: String
) : Exception(message) {
    override fun toString(): String = "CustomException(code='$code', message=$message)"
}