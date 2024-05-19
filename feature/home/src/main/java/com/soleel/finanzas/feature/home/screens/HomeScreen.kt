package com.soleel.finanzas.feature.home.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soleel.finanzas.feature.home.HomeViewModel
import com.soleel.finanzas.feature.home.navigation.HomeBottomNavigationItems

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
//    InitScreen(modifier = modifier, viewModel = viewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel
) {
    val navController = rememberNavController()
    val showBottomSheet = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        bottomBar = {
            navController.currentBackStackEntryAsState().value?.destination?.route?.let {
                HomeNavigationBar(
                    selectedDestination = it,
                    navigateTopLevelDestination = { destination ->
                        if (destination.screenRoute == "add") {
                            showBottomSheet.value = true
                        } else {
                            navController.navigate(destination.screenRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        },
        content = {
            if (showBottomSheet.value) {
//                AddModalBottomSheet(
//                    viewModel,
//                    { showBottomSheet.value = false },
//                    bottomSheetState
//                )
            }

            // Esto no debe estar condicionado, ya que es necesario mantener la ultima Screen visitada
            NavHost(
                navController = navController,
                startDestination = HomeBottomNavigationItems.Init.screenRoute
            ) {
                composable(HomeBottomNavigationItems.Init.screenRoute) {
//                    InitScreen()
                }
                composable(HomeBottomNavigationItems.Stats.screenRoute) {
//                    com.soleel.stats.StatsScreen()
                }
                composable(HomeBottomNavigationItems.Accounts.screenRoute) {
//                    com.soleel.accounts.AccountsScreen()
                }
                composable(HomeBottomNavigationItems.Profile.screenRoute) {
//                    com.soleel.profile.ProfileScreen()
                }
            }
        }
    )
}

@Composable
private fun HomeNavigationBar(
    selectedDestination: String,
    navigateTopLevelDestination: (HomeBottomNavigationItems) -> Unit
) {
    val items = listOf(
        HomeBottomNavigationItems.Init,
        HomeBottomNavigationItems.Stats,
        HomeBottomNavigationItems.Accounts,
        HomeBottomNavigationItems.Profile,
        HomeBottomNavigationItems.Add
    )

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selectedDestination == item.screenRoute,
                onClick = { navigateTopLevelDestination(item) },
            )
        }
    }
}
