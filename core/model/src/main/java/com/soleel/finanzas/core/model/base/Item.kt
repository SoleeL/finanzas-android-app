package com.soleel.finanzas.core.model.base

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class Item(
    val name: String,
    val value: Float,
    val multiply: Float,
    val division: Float,
    val subtract: Float
) : Parcelable