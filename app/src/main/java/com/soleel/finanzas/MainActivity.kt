package com.soleel.finanzas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.soleel.finanzas.ui.theme.FinanzasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // https://developer.android.com/develop/ui/views/launch/splash-screen/migrate
        // https://abhishekgururani.medium.com/googles-recommended-way-to-implement-spash-screen-in-your-android-app-6e56be8196a0

        this.installSplashScreen().apply(
            block = {
                setKeepOnScreenCondition(
                    condition = {
                        mainViewModel.mainUiState.value is MainUiState.Loading
                    }
                )
            }
        )

        super.onCreate(savedInstanceState)

        setContent(
            content = {
                val mainUiState = mainViewModel.mainUiState.collectAsState().value
                val startDestination: Any = when (mainUiState) {
                    is MainUiState.Success -> mainUiState.startDestination
                    is MainUiState.Error -> Error
                    else -> Error
                }

                FinanzasTheme(
                    content = {
                        FinanzasNavigationGraph(startDestination)
                    }
                )
            }
        )
    }
}