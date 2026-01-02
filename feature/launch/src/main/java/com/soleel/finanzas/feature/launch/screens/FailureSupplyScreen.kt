package com.soleel.finanzas.feature.launch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar

@LongDevicePreview
@Composable
fun FailureSupplyScreenPreview() {

    val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
        set("code", "404")
        set("message", "No encontrado")
    }

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    FailureSupplyScreen(
                        failureSupplyViewModel = FailureSupplyViewModel(
                            savedStateHandle = savedStateHandle
                        ),
                        onRetry = { }
                    )
                }
            )
        }
    )

}

@Composable
fun FailureSupplyScreen(
    failureSupplyViewModel: FailureSupplyViewModel = hiltViewModel(),
    onRetry: (() -> Unit)? = null
) {

    val code = failureSupplyViewModel.code
    val message = failureSupplyViewModel.message

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))          // Empuja el contenido al centro vertical

        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = code,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (onRetry != null) {
            Button(
                onClick = { onRetry() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text("Reintentar")
            }
        } else {
            Text(
                text = "No hay acción disponible",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}