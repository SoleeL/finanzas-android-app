package com.soleel.finanzas.feature.configuration

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object ConfigurationGraph

@Serializable
object Language

@Serializable
object Currency

@Serializable
object Payments

@Serializable
object Calendar

@Serializable
object Theme

@Serializable
object Notifications

@Serializable
object Password

@Serializable
object Backup

fun NavGraphBuilder.configurationNavigationGraph(
    navHostController: NavHostController,
    appPreferences: AppPreferences
) {
    navigation<ConfigurationGraph>(startDestination = Language) {
        composable<Language> {
            LanguageScreen(
                onContinue = {
                    navHostController.navigate(Currency)
                }
            )
        }

        composable<Currency> {
            CurrencyScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Payments)
                }
            )
        }

        composable<Payments> {
            PaymentsScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Calendar)
                }
            )
        }

        composable<Calendar> {
            CalendarScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Theme)
                }
            )
        }

        composable<Theme> {
            ThemeScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Notifications)
                }
            )
        }

        composable<Notifications> {
            NotificationsScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Password)
                }
            )
        }

        composable<Password> {
            PasswordScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Backup)
                }
            )
        }

        composable<Backup> {
            BackupScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(
                        route = HomeGraph,
                        builder = {
                            popUpTo(ConfigurationGraph) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
    }
}