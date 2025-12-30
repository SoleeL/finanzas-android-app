package com.soleel.finanzas.feature.launch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.feature.launch.screens.ErrorScreen
import com.soleel.finanzas.feature.launch.screens.SuccessScreen
import com.soleel.finanzas.feature.launch.screens.SupplyingScreen
import kotlinx.serialization.Serializable


@Serializable
object LaunchGraph

fun NavGraphBuilder.launchNavigationGraph() {
    composable<LaunchGraph> {
        LaunchScreen()
    }
}

@Serializable
object Supplying

@Serializable
object Success

@Serializable
object Error

@Composable
fun LaunchScreen(
    launchViewModel: LaunchViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
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

                            composable<Success>(
                                content = {
                                    SuccessScreen()
                                }
                            )

                            composable<Error>(
                                content = {
                                    ErrorScreen(
                                        onRetry = {
                                            navHostController.navigate(Supplying) {
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


//
