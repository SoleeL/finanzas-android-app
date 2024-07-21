package com.soleel.finanzas.core.ui.template

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CancelAlertDialogPreview() {
    CancelAlertDialog(
        onDismiss = {},
        onConfirmation = {},
        dialogTitle = "¿Quieres volver al inicio?",
        dialogText = "Cancelaras la creacion actual."
    )
}

@Composable
fun CancelAlertDialog(
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onConfirmation, content = { Text("Aceptar") }) },
        dismissButton = { TextButton(onClick = onDismiss, content = { Text("Cancelar") }) },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) }
    )
}