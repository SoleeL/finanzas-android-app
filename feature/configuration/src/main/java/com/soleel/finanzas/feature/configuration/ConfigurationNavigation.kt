package com.soleel.finanzas.feature.configuration

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.data.preferences.app.IAppPreferences
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
    backToPrevious: () -> Unit,
    navigateToCurrencyScreen: () -> Unit,
    navigateToPaymentsScreen: () -> Unit,
    navigateToCalendarScreen: () -> Unit,
    navigateToThemeScreen: () -> Unit,
    navigateToNotificationsScreen: () -> Unit,
    navigateToPasswordScreen: () -> Unit,
    navigateToBackupScreen: () -> Unit,
    navigateToHomeGraph: () -> Unit,
    appPreferences: IAppPreferences
) {
    navigation<ConfigurationGraph>(startDestination = Language) {
        composable<Language> {
            LanguageScreen(
                onContinue = navigateToCurrencyScreen
            )
        }

        composable<Currency> {
            CurrencyScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToPaymentsScreen
            )
        }

        composable<Payments> {
            PaymentsScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToCalendarScreen
            )
        }

        composable<Calendar> {
            CalendarScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToThemeScreen
            )
        }

        composable<Theme> {
            ThemeScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToNotificationsScreen
            )
        }

        composable<Notifications> {
            NotificationsScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToPasswordScreen
            )
        }

        composable<Password> {
            PasswordScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToBackupScreen
            )
        }

        composable<Backup> {
            BackupScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToHomeGraph
            )
        }
    }
}