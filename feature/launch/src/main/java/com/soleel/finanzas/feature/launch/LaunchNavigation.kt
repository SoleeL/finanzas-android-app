package com.soleel.finanzas.feature.launch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.core.common.UiState
import com.soleel.finanzas.feature.configuration.ConfigurationGraph
import com.soleel.finanzas.feature.home.HomeGraph
import com.soleel.finanzas.feature.launch.screens.ErrorScreen
import com.soleel.finanzas.feature.launch.screens.SuccessScreen
import com.soleel.finanzas.feature.launch.screens.SupplyingScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable


@Serializable
object LaunchGraph

fun NavGraphBuilder.launchNavigationGraph(
    navigateToConfigurationGraph: () -> Unit,
    navigateToHomeGraph: () -> Unit
) {
    composable<LaunchGraph> {
        LaunchScreen(
            navigateToConfigurationGraph = navigateToConfigurationGraph,
            navigateToHomeGraph = navigateToHomeGraph
        )
    }
}

@Serializable
object Supplying

@Serializable
object SuccessSupply

@Serializable
data class FailureSupply(
    val code: String,
    val message: String
)

@Composable
fun LaunchScreen(
    launchViewModel: LaunchViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController(),
    navigateToConfigurationGraph: () -> Unit,
    navigateToHomeGraph: () -> Unit
) {

    // 1️⃣ Obtenemos el estado como State<UiState<Any>>
    val destinationUiState by launchViewModel.destinationUiState.collectAsState()

    // 2️⃣ Reactiva cuando cambia
    LaunchedEffect(destinationUiState) {
        when (destinationUiState) {
            is UiState.Loading -> {

            }

            is UiState.Success -> {
                navHostController.navigate(
                    route = SuccessSupply
                ) {
                    popUpTo(Supplying) { inclusive = true }
                    launchSingleTop = true
                }

                delay(1_000)

                val destination: Any = (destinationUiState as UiState.Success<Any>).data

                when (destination) {
                    is ConfigurationGraph -> {
                        navigateToConfigurationGraph()
                    }

                    is HomeGraph -> {
                        navigateToHomeGraph()
                    }

                    else -> TODO("NI IDEA SI ESTO PUEDE OCURRIR")
                }
            }

            is UiState.Failure -> {
                val failureUiState = destinationUiState as UiState.Failure
                navHostController.navigate(
                    route = FailureSupply(
                        failureUiState.code,
                        failureUiState.message
                    )
                ) {
                    popUpTo(Supplying) { inclusive = true }
                    launchSingleTop = true
                }

                // TODO: ESTO DEBE REINTENTAR TODO EL PROCESO NAVEGANDO A Supplying7

                // La pantalla de Error ya está en el NavHost;
                // simplemente mostramos la UI con los datos del error.
                // No hacemos nada aquí, pero podrías mostrar un Snackbar si lo deseas.
            }
        }
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween,
                content = {
                    NavHost(
                        navController = navHostController,
                        startDestination = Supplying,
                        builder = {
                            composable<Supplying>(
                                content = {
                                    SupplyingScreen()
                                }
                            )

                            composable<SuccessSupply>(
                                content = {
                                    SuccessScreen()
                                }
                            )

                            composable<FailureSupply>(
                                content = {
                                    ErrorScreen(
                                        onRetry = {
                                            navHostController.navigate(
                                                route = Supplying
                                            ) {
                                                popUpTo(0) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}