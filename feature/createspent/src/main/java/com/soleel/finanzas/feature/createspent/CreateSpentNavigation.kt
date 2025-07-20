package com.soleel.finanzas.feature.createspent

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.finanzas.core.model.Item
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class CreateSpentGraph(val items: List<Item>)


Implementar vista superior y la navegacion como seccion inferior



@Serializable
object SpentTypeSelection

@Serializable
object SpentDateSelection

@Serializable
object SpentNameInput

@Serializable
object AccountTypeSelection

@Serializable
object AccountSelection

@Serializable
object InstalmentSelection

@Serializable
object SpentConfirmation

fun NavGraphBuilder.createSpentNavigationGraph(
    itemsToNavType: Map<KType, NavType<*>>,
    backToPrevious: () -> Unit,
    navigateToSpentDateSelectionScreen: () -> Unit,
    navigateToSpentNameInputScreen: () -> Unit,
    navigateToAccountTypeSelectionScreen: () -> Unit,
    navigateToAccountSelectionScreen: () -> Unit,
    navigateToInstalmentSelectionScreen: () -> Unit,
    navigateToSpentConfirmationScreen: () -> Unit,
    navigateToConfirmed: () -> Unit
) {
    navigation<CreateSpentGraph>(
        startDestination = SpentTypeSelection,
        typeMap = itemsToNavType
    ) {
        composable<SpentTypeSelection> {
            SpentTypeSelectionScreen(
                onContinue = navigateToSpentDateSelectionScreen
            )
        }

        composable<SpentDateSelection> {
            SpentDateSelectionScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToSpentNameInputScreen
            )
        }

        composable<SpentNameInput> {
            SpentNameInputScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToAccountTypeSelectionScreen
            )
        }

        composable<AccountTypeSelection> {
            AccountTypeSelectionScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToAccountSelectionScreen
            )
        }

        composable<AccountSelection> {
            AccountSelectionScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToInstalmentSelectionScreen
            )
        }

        composable<InstalmentSelection> {
            InstalmentSelectionScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToSpentConfirmationScreen
            )
        }

        composable<SpentConfirmation> {
            SpentConfirmationScreen(
                onBackPress = backToPrevious,
                onContinue = navigateToConfirmed
            )
        }
    }
}

//🧾 Nueva plantilla completa:
//"Gasté $[monto] en [nombre del gasto] ([tipo de gasto]) [fecha] [en X artículos] usando mi [nombre de cuenta] [tipo de cuenta][, en X cuotas]."
//

//"I spent $[amount] on [name of expense] ([expense type]) [date phrase] using my [account name] [account type], in [X] instalment(s)."


//🧾 Ejemplos con 1 cuota (sin necesidad de mencionarlo)
//"Gasté $15.000 en supermercado (Alimentos) hoy usando mi tarjeta de débito Cuenta RUT."
//
//"Gasté $8.500 en pasaje de bus (Transporte) ayer usando efectivo."
//
//"Gasté $22.000 en medicamentos (Salud) el 03/05/2025 usando mi tarjeta de crédito BCI."
//
//"Gasté $5.000 en café (Comida) hoy usando mi billetera digital Mach."
//
//"Gasté $45.000 en regalo de cumpleaños (Regalos) el 01/06/2025 usando mi cuenta corriente Banco de Chile."
//
//💳 Ejemplos con múltiples cuotas
//"Gasté $120.000 en televisor (Electrodomésticos) el 10/06/2025 usando mi tarjeta de crédito Santander, en 6 cuotas."
//
//"Gasté $250.000 en notebook (Tecnología) ayer usando mi tarjeta de crédito Falabella, en 12 cuotas."
//
//"Gasté $90.000 en muebles (Hogar) el 05/04/2025 usando mi tarjeta de crédito Cencosud, en 3 cuotas."
//
//"Gasté $60.000 en oftalmólogo (Salud) el 20/03/2025 usando mi tarjeta de crédito BancoEstado, en 2 cuotas."
//
//"Gasté $30.000 en zapatillas (Vestuario) el 18/07/2025 usando mi tarjeta de crédito Visa, en 4 cuotas."
//
//📅 Ejemplos variados con distintas fechas
//"Gasté $3.500 en estacionamiento (Transporte) ayer usando efectivo."
//
//"Gasté $10.000 en comida rápida (Comida) el 15/07/2025 usando mi tarjeta de débito Banco BICE."
//
//"Gasté $5.500 en lavandería (Servicios) hoy usando efectivo."
//
//"Gasté $2.000 en peaje (Viaje) el 01/07/2025 usando mi cuenta MACH."
//
//"Gasté $19.000 en peluquería (Cuidado personal) el 30/06/2025 usando mi tarjeta de débito Itaú."
//

//✅ Ejemplos con 1 ítem (se omite)
//"Gasté $15.000 en supermercado (Alimentos) hoy usando mi tarjeta de débito Cuenta RUT."
//
//"Gasté $8.500 en pasaje de bus (Transporte) ayer usando efectivo."
//
//✅ Ejemplos con múltiples ítems (se incluye)
//"Gasté $22.000 en supermercado (Alimentos) ayer en 4 artículos usando efectivo."
//
//"Gasté $90.000 en farmacia (Salud) el 03/07/2025 en 6 artículos usando mi tarjeta de crédito BCI, en 3 cuotas."
//
//"Gasté $5.000 en comida rápida (Comida) hoy en 2 artículos usando mi cuenta MACH."
//
//"Gasté $250.000 en tienda electrónica (Tecnología) ayer en 7 artículos usando mi tarjeta de crédito Falabella, en 12 cuotas."
//
//🎯 Algunos ejemplos variados:
//"Gasté $3.500 en estacionamiento (Transporte) ayer usando efectivo."
//
//"Gasté $30.000 en farmacia (Salud) el 15/07/2025 en 3 artículos usando mi tarjeta de débito Banco de Chile."
//
//"Gasté $18.000 en librería (Educación) el 12/06/2025 en 5 artículos usando mi tarjeta de débito Scotiabank."
//
//"Gasté $60.000 en muebles (Hogar) el 05/04/2025 en 2 artículos usando mi tarjeta de crédito Cencosud, en 4 cuotas."