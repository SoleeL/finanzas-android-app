package com.soleel.finanzas.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soleel.finanzas.feature.add.AddMenuFAB
import com.soleel.finanzas.core.ui.template.CancelAlertDialog
import com.soleel.finanzas.feature.transactions.navigation.SUMMARY_PERIOD_ARG
import com.soleel.finanzas.feature.transactions.navigation.destination.TransactionsLevelDestination
import com.soleel.finanzas.navigation.FinanzasNavHost
import com.soleel.finanzas.navigation.destination.TopLevelDestination


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinanzasApp(
    appState: FinanzasAppState
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            //            TransactionsTopAppBar(
            //                title = transactionsState.getTitleDestination(),
            //                toTimePeriodSelection = transactionsState.navigateToTimePeriodSelection(),
            //                toSearch = transactionsState.navigateToSearch()
            //            )

            val selectedTabIndex: MutableState<Int> = remember(calculation =  { mutableIntStateOf(0) })

            if (appState.shouldShowTransactionsTab()) {
                TransactionsTab(
                    destinations = appState.transactionsLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTransactions,
                    currentDestination = appState.getCurrentDestination(),
                    selectedTabIndex = selectedTabIndex
                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar()) {
                FinanzasBottomBar(
                    destinations = appState.topLevelDestinations(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.getCurrentDestination(),
                    updateTransactionsTab = appState::updateTransactionsTab,
                    hideExtendAddMenu = appState::hideExtendAddMenu
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
                            toAccountCreate = appState::navigateToAccountCreate,
                            toTransactionCreate = appState::navigateToTransactionCreate
                        )
                    }
                )
            }
        },
        content = {
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
        currentDestination = appState.getCurrentDestination(),
        selectedTabIndex = remember(calculation =  { mutableIntStateOf(0) })
    )
}

@Composable
private fun TransactionsTab(
    destinations: List<TransactionsLevelDestination>,
    onNavigateToDestination: (TransactionsLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    selectedTabIndex: MutableState<Int>
) {
    val currentTransactionsLevel: TransactionsLevelDestination = currentDestination?.getTransactionsLevel() ?: TransactionsLevelDestination.ALL

    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        tabs = {
            destinations.forEachIndexed { index, transactionsLevel ->
                Tab(
                    text = { Text(transactionsLevel.summaryTitle) },
                    selected = currentTransactionsLevel.ordinal == index,
                    enabled = true,
                    onClick = {
                        selectedTabIndex.value = index
                        onNavigateToDestination(TransactionsLevelDestination.entries[index])
                    }
                )
            }
        }
    )
}

fun NavDestination.getTransactionsLevel(): TransactionsLevelDestination {
    val summaryPeriodArg: String = this.arguments[SUMMARY_PERIOD_ARG]?.defaultValue.toString()
    return TransactionsLevelDestination.fromName(summaryPeriodArg)
}

@Composable
private fun FinanzasBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    updateTransactionsTab: () -> Unit,
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
                            updateTransactionsTab()
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
    return this?.hierarchy?.any(
        predicate = {
            it.route?.contains(
                other = destination.name,
                ignoreCase = true
            ) ?: false
        }
    ) ?: false
}