package com.soleel.finanzas.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.finanzas.feature.add.AddMenuFAB
import com.soleel.finanzas.feature.cancelalert.CancelAlertDialog
import com.soleel.finanzas.navigation.FinanzasNavHost
import com.soleel.finanzas.navigation.destination.TopLevelDestination
import com.soleel.finanzas.navigation.destination.TransactionsLevelDestination


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinanzasApp(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
//            TransactionsTopAppBar(
//                title = transactionsState.getTitleDestination(),
//                toTimePeriodSelection = transactionsState.navigateToTimePeriodSelection(),
//                toSearch = transactionsState.navigateToSearch()
//            )
            if (appState.shouldShowTransactionsTab()) {
                TransactionsTab(
                    destinations = appState.transactionsLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTransactionsLevelDestination,
                    currentDestination = appState.getCurrentDestination()
                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                FinanzasBottomBar(
                    destinations = appState.topLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.getCurrentDestination(),
                    showTransactionsTab = appState::showTransactionsTab,
                    hideTransactionsTab = appState::hideTransactionsTab,
                    hideExtendAddMenu = appState::hideExtendAddMenu,
                )
            }
        },
        floatingActionButton = {
            if (appState.shouldShowFloatingAddMenu()) {
                Box(
                    modifier = Modifier
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
                            hideTransactionsTab = appState::hideTransactionsTab,
                            hideBottomBar = appState::hideBottomBar,
                            toCreatePaymentAccount = appState::navigateToPaymentAccountCreate,
                            toCreateTransaction = appState::navigateToTransactionCreate
                        )
                    }
                )
            }
        },
        content = {
            if (appState.shouldShowCancelAlert()) {
                CancelAlertDialog(
                    showTransactionsTab = appState::showTransactionsTab,
                    showBottomBar = appState::showBottomBar,
                    showFloatingAddMenu = appState::showFloatingAddMenu,
                    hideExtendAddMenu = appState::hideExtendAddMenu,
                    onConfirmation = appState::backToHome,
                    onDismissRequest = appState::hideCancelAlert,
                    dialogTitle = "¿Quieres volver al inicio?",
                    dialogText = "Cancelaras la creacion actual."
                )
            }

            FinanzasNavHost(
                modifier = Modifier.padding(it),
                appState = appState
            )
        }
    )
}

@Composable
@Preview
private fun TransactionsTabPreview(
    appState: FinanzasAppState = rememberFinanzasAppState()
) {
    TransactionsTab(
        destinations = TransactionsLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = appState.getCurrentDestination()
    )
}

@Composable
private fun TransactionsTab(
    modifier: Modifier = Modifier,
    destinations: List<TransactionsLevelDestination>,
    onNavigateToDestination: (TransactionsLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    val currentDestinationIndex: Int = currentDestination.getTransactionsLevelIndex()
    TabRow(
        selectedTabIndex = currentDestinationIndex,
        tabs = {
            destinations.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.summaryTitle) },
                    selected = currentDestinationIndex == index,
                    enabled = true,
                    onClick = {
                        Log.d("TransactionsTab", "Tab clicked: ${title.summaryTitle}")
                        onNavigateToDestination(TransactionsLevelDestination.entries[index])
                    },
                )
            }
        }
    )
}

@Composable
private fun FinanzasBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    showTransactionsTab: () -> Unit,
    hideTransactionsTab: () -> Unit,
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
                            if (TopLevelDestination.TRANSACTIONS == destination) {
                                showTransactionsTab()
                            } else {
                                hideTransactionsTab()
                            }

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

private fun NavDestination?.getTransactionsLevelIndex(): Int {
    val topLevelDestination = TransactionsLevelDestination.entries.find(predicate = { destination ->
        this?.route?.contains(destination.name, ignoreCase = true) ?: false
    })
    return topLevelDestination?.ordinal ?: 0
}

private fun NavDestination?.isTransactionsLevelDestinationInHierarchy(index: Int): Boolean {
    return this?.hierarchy?.any(
        predicate = {
            it.route?.contains(
                other = TransactionsLevelDestination.entries[index].name,
                ignoreCase = true
            ) ?: false
        }
    ) ?: false
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any(
        predicate = {
            it.route?.contains(
                other = destination.name,
                ignoreCase = true
            ) ?: false
        }
    ) ?: false
}