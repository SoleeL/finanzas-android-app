package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.core.model.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.theme.TransactionTypeExpenditureBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeIncomeBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeLetterColor
import com.soleel.finanzas.domain.formatdate.AllTransactionsGroupDateUseCase
import com.soleel.finanzas.domain.transformation.visualtransformation.CurrencyVisualTransformation
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsGroupUiState
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel
import java.time.LocalDate


@Composable
internal fun AllTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val allTransactionsGroupUiState: TransactionsGroupUiState by viewModel.allTransactionsUiState.collectAsStateWithLifecycle()

    AllTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        allTransactionsGroupUiState = allTransactionsGroupUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent,
    )
}

@Composable
private fun AllTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    allTransactionsGroupUiState: TransactionsGroupUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit
) {

    val context: Context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack = {
            finishApp(context)
        }
    )

    when (allTransactionsGroupUiState) {
        is TransactionsGroupUiState.Success -> AllTransactionsSuccessScreen(
            allTransactionsGroup = allTransactionsGroupUiState.transactionsGroupList
        )

        is TransactionsGroupUiState.Error -> TransactionsErrorScreen(
            modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) }
        )

        is TransactionsGroupUiState.Loading -> TransactionsLoadingScreen()
    }
}
//
//@Composable
//@Preview
//private fun AllTransactionsSuccessScreenPreview() {
//    Column(modifier = Modifier.background(color = Color.White), content = {
//        AllTransactionsSuccessScreen(
//            allTransactionsGroup = listOf(
//                TransactionsGroup(
//                    localDate = LocalDate.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()), // Fecha 1 (por ejemplo: 1 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "1",
//                                name = "Compra supermercado",
//                                amount = 1500,
//                                date = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()),
//                                createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_MARKET,
//                                accountId = "1",
//                                isDeleted = false,
//                                synchronization = SynchronizationEnum.PENDING
//                            ),
//                            account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 5000,
//                                createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()),
//                                updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641078000000), ZoneId.systemDefault()),
//                                type = AccountTypeEnum.CREDIT,
//                                synchronization = SynchronizationEnum.PENDING
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "2",
//                                name = "Depósito nómina",
//                                amount = 3000,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.INCOME,
//                                category = TransactionCategoryEnum.INCOME_SALARY,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 10000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ), TransactionsGroup(
//                    localDate = LocalDate(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "3",
//                                name = "Pago alquiler",
//                                amount = 1200,
//                                createdAt = LocalDate(1641164400000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
//                                accountId = "3"
//                            ), account = Account(
//                                id = "3",
//                                name = "Cuenta de alquiler",
//                                amount = 3000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ), TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "1",
//                                name = "Compra supermercado",
//                                amount = 1500,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_MARKET,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 5000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "2",
//                                name = "Depósito nómina",
//                                amount = 3000,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.INCOME,
//                                category = TransactionCategoryEnum.INCOME_SALARY,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 10000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "3",
//                                name = "Pago alquiler",
//                                amount = 1200,
//                                createdAt = LocalDate(1641164400000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
//                                accountId = "3"
//                            ), account = Account(
//                                id = "3",
//                                name = "Cuenta de alquiler",
//                                amount = 3000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "1",
//                                name = "Compra supermercado",
//                                amount = 1500,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_MARKET,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 5000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "2",
//                                name = "Depósito nómina",
//                                amount = 3000,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.INCOME,
//                                category = TransactionCategoryEnum.INCOME_SALARY,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 10000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ), TransactionsGroup(
//                    localDate = LocalDate(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "3",
//                                name = "Pago alquiler",
//                                amount = 1200,
//                                createdAt = LocalDate(1641164400000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
//                                accountId = "3"
//                            ), account = Account(
//                                id = "3",
//                                name = "Cuenta de alquiler",
//                                amount = 3000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "1",
//                                name = "Compra supermercado",
//                                amount = 1500,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_MARKET,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 5000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "2",
//                                name = "Depósito nómina",
//                                amount = 3000,
//                                createdAt = LocalDate(1641078000000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.INCOME,
//                                category = TransactionCategoryEnum.INCOME_SALARY,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 10000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ), TransactionsGroup(
//                    localDate = LocalDate(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "3",
//                                name = "Pago alquiler",
//                                amount = 1200,
//                                createdAt = LocalDate(1641164400000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
//                                accountId = "3"
//                            ), account = Account(
//                                id = "3",
//                                name = "Cuenta de alquiler",
//                                amount = 3000,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.DEBIT
//                            )
//                        )
//                    )
//                ),
//
//                TransactionsGroup(
//                    localDate = LocalDate(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
//                    transactionsWithAccount = listOf(
//                        TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "4",
//                                name = "Compra online",
//                                amount = 500,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
//                                accountId = "1"
//                            ), account = Account(
//                                id = "1",
//                                name = "Cuenta principal",
//                                amount = 4500,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.CREDIT
//                            )
//                        ), TransactionWithAccount(
//                            transaction = Transaction(
//                                id = "5",
//                                name = "Transferencia amigo",
//                                amount = 200,
//                                createdAt = LocalDate(1641250800000), // Misma fecha que TransactionsGroup
//                                updatedAt = LocalDate(),
//                                type = TransactionTypeEnum.EXPENDITURE,
//                                category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
//                                accountId = "2"
//                            ), account = Account(
//                                id = "2",
//                                name = "Cuenta nómina",
//                                amount = 9800,
//                                createdAt = LocalDate(),
//                                updatedAt = LocalDate(),
//                                type = AccountTypeEnum.SAVING
//                            )
//                        )
//                    )
//                )
//            )
//        )
//    })
//}

@Composable
private fun AllTransactionsSuccessScreen(
    allTransactionsGroup: List<TransactionsGroup>
) {
    if (allTransactionsGroup.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            content = {
                Text(
                    text = "No existen transacciones actualmente.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        )
    } else {
        AllTransactionList(transactionsGroup = allTransactionsGroup)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AllTransactionList(
    transactionsGroup: List<TransactionsGroup>
) {

    val currencyVisualTransformation by remember(calculation = {
        mutableStateOf(CurrencyVisualTransformation(currencyCode = "USD"))
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 72.dp),
        content = {
            transactionsGroup.forEach(
                action = { group ->
                    stickyHeader(
                        content = {
                            TransactionsGroupDate(localDate = group.localDate)
                        }
                    )

                    items(
                        items = group.transactionsWithAccount,
                        itemContent = { transactionWithAccount ->

                            val transactionTypeColor: Color = if (transactionWithAccount.transaction.type == TransactionTypeEnum.INCOME)
                                TransactionTypeIncomeBackgroundColor else TransactionTypeExpenditureBackgroundColor

                            TransactionGroupItem(
                                transactionTypeIcon = transactionWithAccount.transaction.type.icon,
                                transactionTypeName = transactionWithAccount.transaction.type.value,
                                transactionTypeColor = transactionTypeColor,
                                transactionCategoryIcon = transactionWithAccount.transaction.category.icon,
                                transactionCategoryName = transactionWithAccount.transaction.category.value,
                                transactionName = transactionWithAccount.transaction.name,
                                transactionAmount = currencyVisualTransformation.filter(
                                    AnnotatedString(
                                        text = transactionWithAccount.transaction.amount.toString()
                                    )
                                ).text.toString(),
                                accountTypeName = transactionWithAccount.account.type.value,
                                onClick = {}
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun TransactionsGroupDate(
    localDate: LocalDate
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Start,
        content = {
            Text(
                text = AllTransactionsGroupDateUseCase(localDate),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun TransactionGroupItem(
    transactionTypeIcon: Int,
    transactionTypeName: String,
    transactionTypeColor: Color,
    transactionCategoryIcon: Int,
    transactionCategoryName: String,
    transactionName: String,
    transactionAmount: String,
    accountTypeName: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = {
            TransactionTypeRow(
                transactionTypeIcon = transactionTypeIcon,
                transactionTypeName = transactionTypeName,
                transactionTypeColor = transactionTypeColor
            )
            TransactionDetailRow(
                categoryIcon = transactionCategoryIcon,
                categoryName = transactionCategoryName,
                name = transactionName,
                amount = transactionAmount,
                accountTypeName = accountTypeName
            )
        }
    )
}

@Composable
private fun TransactionTypeRow(
    transactionTypeIcon: Int, transactionTypeName: String, transactionTypeColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = transactionTypeColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = transactionTypeIcon),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(16.dp),
                        tint = TransactionTypeLetterColor
                    )
                    Text(
                        text = transactionTypeName,
                        modifier = Modifier.padding(start = 8.dp),
                        color = TransactionTypeLetterColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    )
}

@Composable
private fun TransactionDetailRow(
    categoryIcon: Int,
    categoryName: String,
    name: String,
    amount: String,
    accountTypeName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(content = {
                Icon(
                    painter = painterResource(id = categoryIcon),
                    contentDescription = "Transaction type",
                    modifier = Modifier.size(48.dp)
                )
            })

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
                                text = categoryName,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = accountTypeName,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            )
        }
    )
}