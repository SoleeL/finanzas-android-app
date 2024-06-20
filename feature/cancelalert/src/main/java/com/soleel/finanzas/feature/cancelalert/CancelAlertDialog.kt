package com.soleel.finanzas.feature.cancelalert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CancelAlertDialogPreview() {
    CancelAlertDialog(
        showTransactionsTab = {},
        showBottomBar = {},
        showFloatingAddMenu = {},
        hideExtendAddMenu = {},
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "¿Quieres volver al inicio?",
        dialogText = "Cancelaras la creacion actual."
    )
}

@Composable
fun CancelAlertDialog(
    showTransactionsTab: () -> Unit,
    showBottomBar: () -> Unit,
    showFloatingAddMenu: () -> Unit,
    hideExtendAddMenu: () -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showTransactionsTab()
                    showBottomBar()
                    showFloatingAddMenu()
                    hideExtendAddMenu()
                    onConfirmation()
                    onDismissRequest()
                },
                content = {
                    Text("Aceptar")
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                content = {
                    Text("Cancelar")
                }
            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        }
    )
}
