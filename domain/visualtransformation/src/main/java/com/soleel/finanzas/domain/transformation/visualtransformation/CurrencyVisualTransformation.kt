package com.soleel.finanzas.domain.transformation.visualtransformation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import java.text.NumberFormat
import java.util.Currency


class CurrencyVisualTransformation(
    currencyCode: String
) : VisualTransformation {

    // README: https://en.wikipedia.org/wiki/ISO_4217
    private val numberFormatter = NumberFormat.getCurrencyInstance().apply(
        block = {
            currency = Currency.getInstance(currencyCode)
            maximumFractionDigits = 0
        }
    )

    override fun filter(text: AnnotatedString): TransformedText {
        /**
         * First we need to trim typed text in case there are any spaces.
         * What can by typed is also handled on TextField itself,
         * see SampleUse code.
         */
        val originalText = text.text.trim()

        if (originalText.isEmpty()) {
            /**
             * If user removed the values there is nothing to format.
             * Calling numberFormatter would cause exception.
             * So we can return text as is without any modification.
             * OffsetMapping.Identity tell system that the number
             * of characters did not change.
             */
            return TransformedText(text, OffsetMapping.Identity)
        }

        if (originalText.isDigitsOnly().not() && null == originalText.toDoubleOrNull()) {
            /**
             * As mentioned before TextField should validate entered data
             * but here we also protect the app from crashing if it doesn't
             * and log warning.
             * Then return same TransformedText like above.
             */
            Log.w(
                "finanzas",
                "Currency visual transformation require using digits only but found [$originalText]"
            )
            return TransformedText(text, OffsetMapping.Identity)
        }

        /**
         * Here is our TextField value transformation to formatted value.
         * EditText operates on String so we have to change it to Int.
         * It's safe at this point because we eliminated cases where
         * value is empty or contains non-digits characters.
         */
        val formattedText = this.numberFormatter.format(originalText.toDouble())

        /**
         * CurrencyOffsetMapping is where the magic happens. See you there :)
         */
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }
}

/**
 * Helper function prevents creating CurrencyVisualTransformation
 * on every re-composition and use inspection mode
 * in case you don't want to use visual filter in Previews.
 * Currencies were displayed for me in Preview but I don't trust them
 * so that's how you could deal with it by returning VisualTransformation.None
 */
@Composable
fun rememberCurrencyVisualTransformation(currency: String): VisualTransformation {
    val inspectionMode = LocalInspectionMode.current
    return remember(currency) {
        if (inspectionMode) {
            VisualTransformation.None
        } else {
            CurrencyVisualTransformation(currency)
        }
    }
}