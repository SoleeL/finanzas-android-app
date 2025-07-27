package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.soleel.finanzas.core.model.Item
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class CreateExpenseGraph(val items: List<Item>)

fun NavGraphBuilder.createExpenseNavigationGraph(
    backToPrevious: () -> Unit,
    itemsToNavType: Map<KType, NavType<List<Item>>>,
) {
    composable<CreateExpenseGraph>(
        typeMap = itemsToNavType,
        content = { backStackEntry ->
            val items: List<Item> = backStackEntry.toRoute<CreateExpenseGraph>().items
            val savedStateHandle: SavedStateHandle = backStackEntry.savedStateHandle
            savedStateHandle["items"] = items
            CreateExpenseScreen(
                backToPrevious = backToPrevious
            )
        }
    )
}


@Serializable
object ExpenseTypeSelection

@Serializable
object AccountTypeSelection

@Serializable
object AccountSelection

@Serializable
object InstalmentSelection

@Serializable
object ExpenseDateSelection

@Serializable
object ExpenseConfirmation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpenseScreen(
    navHostController: NavHostController = rememberNavController(),
    createExpenseViewModel: CreateExpenseViewModel = hiltViewModel(),
    backToPrevious: () -> Unit,
) {
    val currentDestination: NavDestination? = navHostController.currentBackStackEntryAsState()
        .value?.destination
    val isAtStartDestination: Boolean =
        currentDestination?.hasRoute(ExpenseTypeSelection::class) == true

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Agregar gasto")
                },
                modifier = Modifier.background(Color.DarkGray),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isAtStartDestination) {
                                backToPrevious()
                            } else {
                                navHostController.popBackStack()
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    )
                },
                actions = {
                    Button(onClick = backToPrevious) {
                        Text(text = "Cancel")
                    }
                }
            )
        },
        content = { paddingValues ->
            NavHost(
                navController = navHostController,
                startDestination = ExpenseTypeSelection,
                modifier = Modifier.padding(paddingValues),
//                enterTransition = { EnterTransition.None },
//                exitTransition = { ExitTransition.None },
//                popEnterTransition = { EnterTransition.None },
//                popExitTransition = { ExitTransition.None },
                builder = {
                    composable<ExpenseTypeSelection> {
                        ExpenseTypeSelectionScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = { navHostController.navigate(AccountTypeSelection) }
                        )
                    }

                    composable<AccountTypeSelection> {
                        AccountTypeSelectionScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = { navHostController.navigate(AccountSelection) }
                        )
                    }

                    composable<AccountSelection> {
                        AccountSelectionScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = { navHostController.navigate(InstalmentSelection) }
                        )
                    }

                    composable<InstalmentSelection> {
                        InstalmentSelectionScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = { navHostController.navigate(ExpenseDateSelection) }
                        )
                    }

                    composable<ExpenseDateSelection> {
                        ExpenseDateSelectionScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = { navHostController.navigate(ExpenseConfirmation) }
                        )
                    }

                    composable<ExpenseConfirmation> {
                        ExpenseConfirmationScreen(
                            createExpenseViewModel = createExpenseViewModel,
                            onContinue = backToPrevious
                        )
                    }
                }
            )
        }
    )
}