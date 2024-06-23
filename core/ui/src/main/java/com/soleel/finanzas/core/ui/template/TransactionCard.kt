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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.uivalues.PaymentAccountUIValues
import com.soleel.finanzas.core.ui.uivalues.TransactionTypeUIValues
import com.soleel.finanzas.core.ui.uivalues.TransactionUIValues
import com.soleel.finanzas.core.ui.uivalues.getTransactionUI
import java.util.Locale

@Preview
@Composable
fun TransactionTypeIncomeCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.CREDIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.CREDIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryTransferCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_TRANSFER,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategorySalaryCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_SALARY,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryServiceCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_SERVICE,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategorySalesCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_SALES,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryBonusCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_BONUS,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryRefundCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_REFUND,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryOtherCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.CREDIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.INCOME,
            transactionCategory = TransactionCategoryEnum.INCOME_OTHER,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryTransferCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_TRANSFER,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryMarketCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_MARKET,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryServiceCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_SERVICE,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryAcquisitionCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_ACQUISITION,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryLeasureCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_LEASURE,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryGiftCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_GIFT,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryOtherCardPreview() {
    TransactionCard(
        transactionUIValues = getTransactionUI(
            paymentAccountTypeEnum = PaymentAccountTypeEnum.DEBIT,
            paymentAccountName = "CMR Falabella",
            paymentAccountAmount = "$300.000",
            transactionType = TransactionTypeEnum.EXPENDITURE,
            transactionCategory = TransactionCategoryEnum.EXPENDITURE_OTHER,
            transactionName = "Transferencia a juan",
            transactionDate = System.currentTimeMillis(),
            transactionAmount = "$20.000")
    )
}

@Composable
fun TransactionCard(
    transactionUIValues: TransactionUIValues,
    onClick: () -> Unit = {},
    onClickEnable: Boolean = true
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(enabled = onClickEnable, onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            TransactionTypeRow(transactionUIValues.type)
            TransactionPaymentAccountRow(transactionUIValues.paymentAccount)
            TransactionCategoryRow(transactionUIValues)
        }
    )
}

@Composable
fun TransactionTypeRow(
    transactionTypeUIValues: TransactionTypeUIValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = transactionTypeUIValues.backgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = transactionTypeUIValues.icon),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(16.dp),
                        tint = transactionTypeUIValues.letterColor
                    )
                    Text(
                        text = transactionTypeUIValues.name.uppercase(Locale.getDefault()),
                        modifier = Modifier.padding(start = 8.dp),
                        color = transactionTypeUIValues.letterColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    )
}

@Composable
fun TransactionPaymentAccountRow(
    paymentAccountUIValues: PaymentAccountUIValues
) {
    Column(
        modifier = Modifier.background(brush = paymentAccountUIValues.type.gradientBrush),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                painter = painterResource(id = paymentAccountUIValues.type.icon),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = paymentAccountUIValues.type.letterColor
                            )
                            Text(
                                text = paymentAccountUIValues.type.name,
                                modifier = Modifier.padding(start = 8.dp),
                                color = paymentAccountUIValues.type.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    Text(
                        text = paymentAccountUIValues.amount,
                        modifier = Modifier,
                        color = paymentAccountUIValues.type.letterColor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
//                            .padding(16.dp),
                content = {
                    Text(
                        modifier = Modifier,
                        text = paymentAccountUIValues.name,
                        color = paymentAccountUIValues.type.letterColor,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}

@Composable
fun TransactionCategoryRow(
    transactionUIValues: TransactionUIValues
) {
    if (null == transactionUIValues.category) {
        return
    }

    Column(
        modifier = Modifier.background(brush = transactionUIValues.category.gradientBrush),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                painter = painterResource(
                                    id = transactionUIValues.category.icon
                                ),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = transactionUIValues.category.letterColor
                            )
                            Text(
                                text = transactionUIValues.category.name,
                                modifier = Modifier.padding(start = 8.dp),
                                color = transactionUIValues.category.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    Text(
                        text = transactionUIValues.amount,
                        modifier = Modifier,
                        color = transactionUIValues.category.letterColor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
//                            .padding(16.dp),
                content = {
                    Text(
                        modifier = Modifier,
                        text = transactionUIValues.name,
                        color = transactionUIValues.category.letterColor,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}