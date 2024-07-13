package com.soleel.finanzas.feature.transactions.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.template.AllTransactionItemDetail
import com.soleel.finanzas.core.ui.theme.TransactionTypeExpenditureBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeIncomeBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeLetterColor
import com.soleel.finanzas.feature.transactions.TransactionsErrorScreen
import com.soleel.finanzas.feature.transactions.TransactionsGroupUiState
import com.soleel.finanzas.feature.transactions.TransactionsLoadingScreen
import com.soleel.finanzas.feature.transactions.TransactionsUiEvent
import com.soleel.finanzas.feature.transactions.TransactionsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun AllTransactionsListRoute(
    modifier: Modifier = Modifier,
    finishApp: (Context) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val allTransactionsGroupUiState: TransactionsGroupUiState by viewModel.allTransactionsUiState.collectAsState()

    AllTransactionsListScreen(
        modifier = modifier,
        finishApp = finishApp,
        allTransactionsGroupUiState = allTransactionsGroupUiState,
        onTransactionsUiEvent = viewModel::onTransactionsUiEvent
    )
}

@Composable
fun AllTransactionsListScreen(
    modifier: Modifier,
    finishApp: (Context) -> Unit,
    allTransactionsGroupUiState: TransactionsGroupUiState,
    onTransactionsUiEvent: (TransactionsUiEvent) -> Unit
) {

    val context: Context = LocalContext.current

    BackHandler(enabled = true, onBack = {
        finishApp(context)
    })

    when (allTransactionsGroupUiState) {
        is TransactionsGroupUiState.Success -> AllTransactionsSuccessScreen(
            allTransactionsGroupUiState.transactionsGroup
        )

        is TransactionsGroupUiState.Error -> TransactionsErrorScreen(modifier = modifier,
            onRetry = { onTransactionsUiEvent(TransactionsUiEvent.Retry) })

        is TransactionsGroupUiState.Loading -> TransactionsLoadingScreen()
    }
}

@Composable
@Preview
fun AllTransactionsSuccessScreenPreview() {
    Column(
        modifier = Modifier.background(color = Color.White),
        content = {
            AllTransactionsSuccessScreen(
                allTransactionsGroup = listOf(
                    TransactionsGroup(
                        date = Date(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "1",
                                    name = "Compra supermercado",
                                    amount = 1500,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_MARKET,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 5000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "2",
                                    name = "Depósito nómina",
                                    amount = 3000,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.INCOME,
                                    category = TransactionCategoryEnum.INCOME_SALARY,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 10000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),
                    TransactionsGroup(
                        date = Date(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "3",
                                    name = "Pago alquiler",
                                    amount = 1200,
                                    createAt = Date(1641164400000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
                                    accountId = "3"
                                ),
                                account = Account(
                                    id = "3",
                                    name = "Cuenta de alquiler",
                                    amount = 3000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),
                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "1",
                                    name = "Compra supermercado",
                                    amount = 1500,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_MARKET,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 5000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "2",
                                    name = "Depósito nómina",
                                    amount = 3000,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.INCOME,
                                    category = TransactionCategoryEnum.INCOME_SALARY,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 10000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),
                    TransactionsGroup(
                        date = Date(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "3",
                                    name = "Pago alquiler",
                                    amount = 1200,
                                    createAt = Date(1641164400000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
                                    accountId = "3"
                                ),
                                account = Account(
                                    id = "3",
                                    name = "Cuenta de alquiler",
                                    amount = 3000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "1",
                                    name = "Compra supermercado",
                                    amount = 1500,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_MARKET,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 5000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "2",
                                    name = "Depósito nómina",
                                    amount = 3000,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.INCOME,
                                    category = TransactionCategoryEnum.INCOME_SALARY,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 10000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),
                    TransactionsGroup(
                        date = Date(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "3",
                                    name = "Pago alquiler",
                                    amount = 1200,
                                    createAt = Date(1641164400000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
                                    accountId = "3"
                                ),
                                account = Account(
                                    id = "3",
                                    name = "Cuenta de alquiler",
                                    amount = 3000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641078000000), // Fecha 1 (por ejemplo: 1 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "1",
                                    name = "Compra supermercado",
                                    amount = 1500,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_MARKET,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 5000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "2",
                                    name = "Depósito nómina",
                                    amount = 3000,
                                    createAt = Date(1641078000000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.INCOME,
                                    category = TransactionCategoryEnum.INCOME_SALARY,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 10000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),
                    TransactionsGroup(
                        date = Date(1641164400000), // Fecha 2 (por ejemplo: 2 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "3",
                                    name = "Pago alquiler",
                                    amount = 1200,
                                    createAt = Date(1641164400000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_SERVICE,
                                    accountId = "3"
                                ),
                                account = Account(
                                    id = "3",
                                    name = "Cuenta de alquiler",
                                    amount = 3000,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.DEBIT
                                )
                            )
                        )
                    ),

                    TransactionsGroup(
                        date = Date(1641250800000), // Fecha 3 (por ejemplo: 3 de enero de 2022)
                        transactionsWithAccount = listOf(
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "4",
                                    name = "Compra online",
                                    amount = 500,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
                                    accountId = "1"
                                ),
                                account = Account(
                                    id = "1",
                                    name = "Cuenta principal",
                                    amount = 4500,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.CREDIT
                                )
                            ),
                            TransactionWithAccount(
                                transaction = Transaction(
                                    id = "5",
                                    name = "Transferencia amigo",
                                    amount = 200,
                                    createAt = Date(1641250800000), // Misma fecha que TransactionsGroup
                                    updatedAt = Date(),
                                    type = TransactionTypeEnum.EXPENDITURE,
                                    category = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
                                    accountId = "2"
                                ),
                                account = Account(
                                    id = "2",
                                    name = "Cuenta nómina",
                                    amount = 9800,
                                    createAt = Date(),
                                    updatedAt = Date(),
                                    type = AccountTypeEnum.SAVING
                                )
                            )
                        )
                    )
                )
            )
        })
}

@Composable
fun AllTransactionsSuccessScreen(
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
        AllTransactionList(allTransactionsGroup = allTransactionsGroup)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllTransactionList(
    allTransactionsGroup: List<TransactionsGroup>
) {
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            allTransactionsGroup.forEach(
                action = { group ->
                    stickyHeader(
                        content = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Start,
                                content = {
                                    Text(
                                        text = simpleDateFormat.format(group.date),
                                        style = TextStyle(fontWeight = FontWeight.Bold),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp, horizontal = 16.dp)
                                    )
                                }
                            )
                        }
                    )

                    itemsIndexed(
                        items = group.transactionsWithAccount,
                        itemContent = { index, transactionWithAccount ->
                            TransactionItem(
                                currentIndex = index,
                                lastIndex = group.transactionsWithAccount.lastIndex,
                                transactionWithAccount = transactionWithAccount,
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
fun TransactionItem(
    currentIndex: Int,
    lastIndex: Int,
    transactionWithAccount: TransactionWithAccount,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            TransactionTypeRow(transactionTypeEnum = transactionWithAccount.transaction.type)
            TransactionDetailRow(transactionWithAccount = transactionWithAccount)
        }
    )
}

@Composable
fun TransactionTypeRow(
    transactionTypeEnum: TransactionTypeEnum
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (TransactionTypeEnum.INCOME == transactionTypeEnum) TransactionTypeIncomeBackgroundColor else TransactionTypeExpenditureBackgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = if (TransactionTypeEnum.INCOME == transactionTypeEnum) R.drawable.ic_income else R.drawable.ic_expenditure),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(16.dp),
                        tint = TransactionTypeLetterColor
                    )
                    Text(
                        text = transactionTypeEnum.value.uppercase(Locale.getDefault()),
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
fun TransactionDetailRow(
    transactionWithAccount: TransactionWithAccount
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(
                content = {
                    Icon(
                        painter = painterResource(id = if (TransactionTypeEnum.INCOME == transactionWithAccount.transaction.type) R.drawable.ic_income else R.drawable.ic_expenditure),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(36.dp)
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(
                        content = {
                            Text(
                                text = transactionWithAccount.transaction.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = transactionWithAccount.transaction.createAt.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        content = {
                            Text(
                                text = transactionWithAccount.transaction.amount.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = transactionWithAccount.account.type.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            )
        }
    )
}