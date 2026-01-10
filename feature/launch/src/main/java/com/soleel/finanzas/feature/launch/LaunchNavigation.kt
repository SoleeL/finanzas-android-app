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
import com.soleel.finanzas.feature.launch.screens.FailureSupplyScreen
import com.soleel.finanzas.feature.launch.screens.SuccessSupplyScreen
import com.soleel.finanzas.feature.launch.screens.SupplyingScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable


@Serializable
object LaunchGraph

fun NavGraphBuilder.launchNavigationGraph(
    navigateToConfigurationGraph: () -> Unit,
    navigateToCreateAccountGraph: () -> Unit,
    navigateToHomeGraph: () -> Unit
) {
    composable<LaunchGraph> {
        LaunchScreen(
            navigateToConfigurationGraph = navigateToConfigurationGraph,
            navigateToCreateAccountGraph = navigateToCreateAccountGraph,
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
    navigateToCreateAccountGraph: () -> Unit,
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

                val navigateTo: LaunchUiNavigation =
                    (destinationUiState as UiState.Success<LaunchUiNavigation>).data

                when (navigateTo) {
                    LaunchUiNavigation.ToConfiguration -> navigateToConfigurationGraph()
                    LaunchUiNavigation.ToCreateAccount -> navigateToCreateAccountGraph()
                    LaunchUiNavigation.ToHome -> navigateToHomeGraph()
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
                                    SuccessSupplyScreen()
                                }
                            )

                            composable<FailureSupply>(
                                content = {
                                    FailureSupplyScreen(
                                        onRetry = {
                                            navHostController.navigate(
                                                route = Supplying
                                            ) {
                                                popUpTo(0) { inclusive = true }
                                                launchSingleTop = true
                                            }

                                            launchViewModel.retry()
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