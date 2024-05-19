package com.soleel.finanzas.core.common.argument

fun <T, R> ((T) -> R).withArgument(argument: T): () -> R {
    return { this(argument) }
}