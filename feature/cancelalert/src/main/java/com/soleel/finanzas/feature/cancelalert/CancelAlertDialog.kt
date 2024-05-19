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
                    showBottomBar()
                    showFloatingAddMenu()
                    hideExtendAddMenu()
                    onConfirmation()
                    onDismissRequest()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        }
    )
}
