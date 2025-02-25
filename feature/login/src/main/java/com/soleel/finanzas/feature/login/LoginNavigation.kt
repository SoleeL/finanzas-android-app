package com.soleel.finanzas.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.data.preferences.AppPreferences
import kotlinx.serialization.Serializable

@Serializable
object LoginGraph

@Serializable
object Login

@Serializable
object Signup

fun NavGraphBuilder.loginNavigationGraph(
    navHostController: NavHostController,
    appPreferences: AppPreferences
) {
    navigation<LoginGraph>(startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onLoginClick = {
                    navHostController.navigate(
                        route = HomeGraph,
                        builder = {
                            popUpTo(LoginGraph) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                },
                onRegisterClick = {
                    navHostController.navigate(Signup)
                }
            )
        }

        composable<Signup> {
            SignupScreen(
                onBackPress = {
                    navHostController.popBackStack() // Regresa a Login
                },
                onContinue = {
                    navHostController.navigate(
                        route = ConfigurationGraph,
                        builder = {
                            popUpTo(LoginGraph) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
    }
}
