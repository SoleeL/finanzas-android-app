package com.soleel.finanzas.feature.createaccount

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable


@Serializable
object CreateAccountGraph

fun NavGraphBuilder.createAccountNavigationGraph(
    navigateToHomeGraph: () -> Unit
) {
    composable<CreateAccountGraph> {
        CreateAccountScreen(
            navigateToHomeGraph = navigateToHomeGraph
        )
    }
}

@Composable
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController(),
    navigateToHomeGraph: () -> Unit
) {

}

//fun NavGraphBuilder.createAccountScreen(
//    onBackToPreviousView: () -> Unit
//) {
//    composable(
//        route = CREATE_ACCOUNT_ROUTE,
//        enterTransition = { fadeIn(initialAlpha = 1f) },
//        exitTransition = { fadeOut(targetAlpha = 1f) },
//        popEnterTransition = { fadeIn(initialAlpha = 1f) },
//        popExitTransition = { fadeOut(targetAlpha = 1f) },
//        content = {
//            CreateAccountRoute(
//                onBackToPreviousView = onBackToPreviousView
//            )
//        }
//    )
//}