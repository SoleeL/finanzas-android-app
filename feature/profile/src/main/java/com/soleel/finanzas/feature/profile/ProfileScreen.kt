package com.soleel.finanzas.feature.profile

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun ProfileRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        modifier = modifier,
        finishApp = finishApp,
        viewModel = viewModel
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    viewModel: ProfileViewModel
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "AllTransactionsListScreen: back press")
            finishApp(context)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}