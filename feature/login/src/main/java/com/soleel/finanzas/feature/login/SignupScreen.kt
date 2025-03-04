package com.soleel.finanzas.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soleel.finanzas.core.ui.utils.SmartphonePreview

@SmartphonePreview
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onBackPress = { /* Simulación de navegación */ },
        onContinue = { /* Simulación de navegación */ }
    )
}

@Composable
fun SignupScreen(
    onBackPress: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Signup screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackPress) {
            Text(text = "Back to Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onContinue) {
            Text(text = "Continue to Configuration")
        }
    }
}