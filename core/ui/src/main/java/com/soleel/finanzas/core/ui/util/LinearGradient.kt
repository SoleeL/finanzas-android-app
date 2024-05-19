package com.soleel.finanzas.core.ui.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


fun getCardLinearGradient(gradient1: Color, gradient2: Color): Brush {
    return Brush.linearGradient(
        colors = listOf(gradient1, gradient2),
        start = Offset(0f, 0f),
        end = Offset(450f, 0f)
    )
}