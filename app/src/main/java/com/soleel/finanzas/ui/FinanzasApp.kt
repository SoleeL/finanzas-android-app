package com.soleel.finanzas.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.add.AddMenuFAB
import com.soleel.cancelalert.CancelAlertDialog
import com.soleel.finanzas.navigation.FinanzasNavHost
import com.soleel.finanzas.navigation.TopLevelDestination


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinanzasApp(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {
//    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                FinanzasBottomBar(
                    destinations = appState.topLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.getCurrentDestination(),
                    hideExtendAddMenu = appState::hideExtendAddMenu,
                )
            }
        },
        floatingActionButton = {
            if (appState.shouldShowFloatingAddMenu()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                if (appState.shouldShowExtendAddMenu()) {
                                    appState.hideExtendAddMenu()
                                }
                            })
                        },
                    contentAlignment = Alignment.BottomEnd,
                    content = {
                        AddMenuFAB(
                            hideFloatingAddMenu = appState::hideFloatingAddMenu,
                            shouldShowExtendAddMenu = appState.shouldShowExtendAddMenu(),
                            showExtendAddMenu = appState::showExtendAddMenu,
                            hideExtendAddMenu = appState::hideExtendAddMenu,
                            hideBottomBar = appState::hideBottomBar,
                            toCreatePaymentAccount = appState::navigateToCreatePaymentAccount,
                            toCreateTransaction = appState::navigateToCreateTransaction
                        )
                    }
                )

            }
        },
        content = {
            if (appState.shouldShowCancelAlert()) {
                CancelAlertDialog(
                    showBottomBar = appState::showBottomBar,
                    showFloatingAddMenu = appState::showFloatingAddMenu,
                    hideExtendAddMenu = appState::hideExtendAddMenu,
                    onConfirmation = appState::backToHome,
                    onDismissRequest = appState::hideCancelAlert,
                    dialogTitle = "¿Quieres volver al inicio?",
                    dialogText = "Cancelaras la creacion actual."
                )
            }

            FinanzasNavHost(appState = appState)
        }
    )
}


@Composable
private fun FinanzasBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    hideExtendAddMenu: () -> Unit,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        content = {
            destinations.forEach(
                action = { destination ->
                    val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            hideExtendAddMenu()
                            onNavigateToDestination(destination)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = destination.unselectedIcon),
                                contentDescription = destination.titleTextId
                            )
                        },
                        label = { Text(destination.titleTextId) },
                    )
                }
            )
        }
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}