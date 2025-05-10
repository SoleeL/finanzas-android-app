package com.soleel.finanzas

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.data.preferences.app.MockAppPreferences
import com.soleel.finanzas.feature.configuration.Backup
import com.soleel.finanzas.feature.configuration.Calendar
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.configuration.Currency
import com.soleel.finanzas.feature.configuration.Notifications
import com.soleel.finanzas.feature.configuration.Password
import com.soleel.finanzas.feature.configuration.Payments
import com.soleel.finanzas.feature.configuration.Theme
import com.soleel.finanzas.feature.configuration.configurationNavigationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.home.homeNavigationGraph
import com.soleel.finanzas.feature.login.LoginGraph
import com.soleel.finanzas.feature.login.Signup
import com.soleel.finanzas.feature.login.loginNavigationGraph
import com.soleel.finanzas.feature.menu.menuNavigationGraph
import kotlinx.serialization.Serializable

@LongDevicePreview
@Composable
fun FinanzasNavigationGraphPreview() {
   FinanzasNavigationGraph(MockAppPreferences())
}

@Serializable
object AddGraph // Flujo invocado por la calculadora para ingresar una transaccion

@Serializable
object Loading

@Serializable
object Error

@Composable
fun FinanzasNavigationGraph(
    startDestination: Any
) {
    val navHostController: NavHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        builder = {
            composable<Loading> {
                SplashScreen()
            }

            composable<Error> {
                ErrorScreen()
            }

            loginNavigationGraph(
                navigateToHomeGraph = {
                    navHostController.navigate(
                        route = HomeGraph,
                        builder = {
                            popUpTo(route = LoginGraph, popUpToBuilder = { inclusive = true })
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                },
                navigateToSignupScreen = { navHostController.navigate(Signup) },
                backToLoginScreen = { navHostController.popBackStack() }
            ) {
                navHostController.navigate(
                    route = ConfigurationGraph,
                    builder = {
                        popUpTo(
                            route = LoginGraph,
                            popUpToBuilder = { inclusive = true })
                    }
                )
            }

            configurationNavigationGraph(
                backToPrevious = { navHostController.popBackStack() },
                navigateToCurrencyScreen = { navHostController.navigate(Currency) },
                navigateToPaymentsScreen = { navHostController.navigate(Payments) },
                navigateToCalendarScreen = { navHostController.navigate(Calendar) },
                navigateToThemeScreen = { navHostController.navigate(Theme) },
                navigateToNotificationsScreen = { navHostController.navigate(Notifications) },
                navigateToPasswordScreen = { navHostController.navigate(Password) },
                navigateToBackupScreen = { navHostController.navigate(Backup) }
            ) {
                navHostController.navigate(
                    route = HomeGraph,
                    builder = {
                        popUpTo(
                            route = ConfigurationGraph,
                            popUpToBuilder = { inclusive = true })
                    }
                )
            }

            homeNavigationGraph()

            menuNavigationGraph()
        }
    )
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun ErrorScreen(
    onRetry: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Algo salió mal",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            onRetry?.let {
                Button(onClick = it) {
                    Text("Reintentar")
                }
            }
        }
    }
}

