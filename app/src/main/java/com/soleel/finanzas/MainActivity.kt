package com.soleel.finanzas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.soleel.finanzas.ui.FinanzasApp
import com.soleel.finanzas.ui.rememberFinanzasAppState
import com.soleel.finanzas.ui.theme.FinanzasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(content = {
            val appState = rememberFinanzasAppState()

            FinanzasTheme(
                content = {
                    FinanzasApp(appState)
                }
            )
        })
    }
}