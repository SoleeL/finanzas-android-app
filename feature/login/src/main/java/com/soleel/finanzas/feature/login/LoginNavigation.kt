package com.soleel.finanzas.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object LoginGraph

@Serializable
object Login

@Serializable
object Signup

fun NavGraphBuilder.loginNavigationGraph(
    navigateToHomeGraph: () -> Unit,
    navigateToSignupScreen: () -> Unit,
    backToLoginScreen: () -> Unit,
    navigateToConfigureGraph: () -> Unit
) {
    navigation<LoginGraph>(startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onLoginClick = navigateToHomeGraph,
                onRegisterClick = navigateToSignupScreen
            )
        }

        composable<Signup> {
            SignupScreen(
                onBackPress = backToLoginScreen,
                onContinue = navigateToConfigureGraph
            )
        }
    }
}
