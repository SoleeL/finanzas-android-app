package com.soleel.finanzas.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val theme: String,
    val notificationsEnabled: Boolean
)