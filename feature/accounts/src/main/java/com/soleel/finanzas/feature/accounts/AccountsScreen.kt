package com.soleel.finanzas.feature.accounts

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.core.component.onSingleClick
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import java.time.LocalDateTime


@Composable
internal fun AccountsRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val accountsUiState: AccountsUiState by viewModel.accountsUiState.collectAsStateWithLifecycle()

    AccountsScreen(
        modifier = modifier,
        finishApp = finishApp,
        accountsUiState = accountsUiState,
        onAccountsUiEvent = viewModel::onAccountsUiEvent,
        singleEventManager = viewModel.singleEventManager
    )
}

@Composable
fun AccountsScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    accountsUiState: AccountsUiState,
    onAccountsUiEvent: (AccountsUiEvent) -> Unit,
    singleEventManager: SingleEventManager
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            Log.d("finanzas", "AllTransactionsListScreen: back press")
            finishApp(context)
        }
    )

    when (accountsUiState) {
        is AccountsUiState.Success -> AccountsSuccessScreen(
            accounts = accountsUiState.accounts,
            singleEventManager = singleEventManager
        )

        is AccountsUiState.Error -> AccountsErrorScreen(
            modifier = modifier,
            onRetry = { onAccountsUiEvent(AccountsUiEvent.Retry) }
        )

        is AccountsUiState.Loading -> AccountsLoadingScreen()
    }
}

@Preview
@Composable
fun AccountsSuccessScreenPreview() {
    AccountsSuccessScreen(
        accounts = listOf(
            Account(
                id = "0",
                type = AccountTypeEnum.CREDIT,
                name = "CMR Falabella",
                totalIncome = 50000,
                totalExpense = 50000,
                totalAmount = 100000,
                transactionsNumber = 10,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isDeleted = false,
                synchronization = SynchronizationEnum.PENDING
            ),
            Account(
                id = "0",
                type = AccountTypeEnum.DEBIT,
                name = "Cuenta corriente Falabella",
                totalIncome = 500000,
                totalExpense = 500000,
                totalAmount = 1000000,
                transactionsNumber = 100,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isDeleted = false,
                synchronization = SynchronizationEnum.PENDING
            ),
            Account(
                id = "0",
                type = AccountTypeEnum.CASH,
                name = "Billetera",
                totalIncome = 5000,
                totalExpense = 5000,
                totalAmount = 10000,
                transactionsNumber = 3,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isDeleted = false,
                synchronization = SynchronizationEnum.PENDING
            ),
            Account(
                id = "0",
                type = AccountTypeEnum.INVESTMENT,
                name = "App Racional",
                totalIncome = 900000,
                totalExpense = 0,
                totalAmount = 900000,
                transactionsNumber = 3,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                isDeleted = false,
                synchronization = SynchronizationEnum.PENDING
            )
        ),
        singleEventManager = SingleEventManager()
    )
}

@Composable
fun AccountsSuccessScreen(
    accounts: List<Account>,
    singleEventManager: SingleEventManager
) {
    if (accounts.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            content = {
                Text(
                    text = "No existen cuentas actualmente.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        )
    } else {
        AccountsList(
            accounts = accounts,
            singleEventManager = singleEventManager
        )
    }
}

@Composable
fun AccountsList(
    accounts: List<Account>,
    singleEventManager: SingleEventManager
) {
    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 72.dp),
        content = {
            accounts.forEach(
                action = { account: Account ->
                    // README: Quizas mas adelante agregar un stickyHeader para separa por tipos de cuenta

                    item(
                        content = {
                            AccountItem(
                                typeIcon = account.type.icon,
                                typeName = account.type.value,
                                name = account.name,
                                amount = currencyVisualTransformation.filter(
                                    AnnotatedString(
                                        text = account.totalAmount.toString()
                                    )
                                ).text.toString(),
                                transactionNumber = account.transactionsNumber,
                                onClick = { Log.d("finanzas", "Click en ${account.name}")},
                                singleEventManager = singleEventManager
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun AccountItem(
    typeIcon: Int,
    typeName: String,
    name: String,
    amount: String,
    transactionNumber: Int,
    onClick: () -> Unit,
    singleEventManager: SingleEventManager = SingleEventManager(),
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onSingleClick(
                singleEventManager = singleEventManager,
                onClick = onClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        content = {
                            Icon(
                                painter = painterResource(id = typeIcon),
                                contentDescription = "Account type",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    )

                    Column(
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.titleLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = amount,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Text(
                                        text = typeName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        text = "$transactionNumber transacciones",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun AccountsErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Error de carga",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hubo un problema al cargar los datos. Inténtalo de nuevo.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun AccountsLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            CircularProgressIndicator(
                color = ProgressIndicatorDefaults.circularColor,
                strokeWidth = 5.dp,
                trackColor = ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )
        }
    )
}

