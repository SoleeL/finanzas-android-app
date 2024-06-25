package com.soleel.finanzas.core.ui.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.uivalues.TransactionUIValues
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI


@Preview
@Composable
fun AllTransactionItemINCOMEPreview() {
    AllTransactionItem(
        transactionUIValues = getTransactionUI(
            accountTypeEnum = AccountTypeEnum.CREDIT,
            accountName = "CMR Falabella",
            accountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_TRANSFER,
            transactionName = "Transferencia a juan",
            transactionDate = "30/05/1994 01:12",
            transactionAmount = "$20.000"
        ),
        onClick = {}
    )
}

@Preview
@Composable
fun AllTransactionItemEXPENDITUREPreview() {
    AllTransactionItem(
        transactionUIValues = getTransactionUI(
            accountTypeEnum = AccountTypeEnum.CREDIT,
            accountName = "CMR Falabella",
            accountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_GIFT,
            transactionName = "Juego en steam a juan",
            transactionDate = "30/05/1994 01:12",
            transactionAmount = "$20.000"
        ),
        onClick = {}
    )
}

@Composable
fun AllTransactionItem(
    transactionUIValues: TransactionUIValues,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            TransactionTypeRow(transactionTypeUIValues = transactionUIValues.type)
            AllTransactionItemDetail(transactionUIValues = transactionUIValues)
        }
    )
}

@Composable
fun AllTransactionItemDetail(
    transactionUIValues: TransactionUIValues
) {
    if (null == transactionUIValues.category) {
        return
    }

    Row(
        modifier = Modifier
            .background(brush = transactionUIValues.category.gradientBrush)
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                painter = painterResource(id = transactionUIValues.category.icon),
                contentDescription = "Add button.",
                modifier = Modifier.size(48.dp),
                tint = transactionUIValues.category.letterColor
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
                                text = transactionUIValues.name,
                                color = transactionUIValues.category.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = transactionUIValues.date,
                                color = transactionUIValues.category.letterColor,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        content = {
                            Text(
                                text = transactionUIValues.amount,
                                color = transactionUIValues.category.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = transactionUIValues.account.type.name,
                                color = transactionUIValues.category.letterColor,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            )
        }
    )
}