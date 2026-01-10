package com.soleel.finanzas.core.ui.visualtransaformations

import android.icu.text.DecimalFormat
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class TwoDecimalTransformation : VisualTransformation {
    private val decimalFormat = DecimalFormat("#.##") // hasta 2 decimales

    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text.toFloatOrNull() ?: 0f
        val formatted = decimalFormat.format(original)

        val transformedText = AnnotatedString(formatted)

        // OffsetMapping identidad, porque no necesitas mapear posiciones
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = formatted.length
            override fun transformedToOriginal(offset: Int): Int = text.text.length
        }

        return TransformedText(transformedText, offsetMapping)
    }
}