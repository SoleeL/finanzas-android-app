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
import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
import com.soleel.finanzas.core.common.constants.TransactionCategoryConstant
import com.soleel.finanzas.core.common.constants.TransactionTypeConstant
import com.soleel.finanzas.core.ui.util.PaymentAccountCardItem
import com.soleel.finanzas.core.ui.util.TransactionCategoryCardItem
import com.soleel.finanzas.core.ui.util.TransactionTypeCardItem
import com.soleel.finanzas.core.ui.util.getPaymentAccountCard
import com.soleel.finanzas.core.ui.util.getTransactionCategoryCard
import com.soleel.finanzas.core.ui.util.getTransactionTypeCard
import java.util.Locale

@Preview
@Composable
fun TransactionTypeCategoryCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.CREDIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.CREDIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryTransferCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_TRANSFER,
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategorySalaryCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_SALARY
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryServiceCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_SERVICE
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategorySalesCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_SALES
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryBonusCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_BONUS
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryRefundCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_REFUND
        )
    )
}

@Preview
@Composable
fun TransactionTypeIncomeCategoryOtherCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.INCOME
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.INCOME,
            transactionCategory = TransactionCategoryConstant.INCOME_OTHER
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryTransferCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_TRANSFER
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryMarketCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_MARKET
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryServiceCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_SERVICE
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryAcquisitionCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_ACQUISITION
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryLeasureCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_LEASURE
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryGiftCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_GIFT
        )
    )
}

@Preview
@Composable
fun TransactionTypeExpenditureCategoryOtherCardPreview() {
    TransactionCard(
        transactionTypeCardItem = getTransactionTypeCard(
            transactionType = TransactionTypeConstant.EXPENDITURE
        ),
        paymentAccountCardItem = getPaymentAccountCard(
            paymentAccountType = PaymentAccountTypeConstant.DEBIT,
            paymentAccountTypeName = "Cuenta de credito banco estado",
            amount = "$300,000"
        ),
        transactionCategoryCardItem = getTransactionCategoryCard(
            transactionType = TransactionTypeConstant.EXPENDITURE,
            transactionCategory = TransactionCategoryConstant.EXPENDITURE_OTHER
        )
    )
}

@Composable
fun TransactionCard(
    transactionTypeCardItem: TransactionTypeCardItem,
    paymentAccountCardItem: PaymentAccountCardItem,
    transactionCategoryCardItem: TransactionCategoryCardItem? = null,
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
            TransactionTypeRow(transactionTypeCardItem)
            TransactionPaymentAccountRow(paymentAccountCardItem)
            if (null != transactionCategoryCardItem) {
                TransactionCategoryRow(transactionCategoryCardItem)
            }
        }
    )
}

@Composable
fun TransactionTypeRow(
    transactionTypeCardItem: TransactionTypeCardItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = transactionTypeCardItem.typeBackgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = transactionTypeCardItem.typeIcon),
                        contentDescription = "Transaction type",
                        modifier = Modifier.size(16.dp),
                        tint = transactionTypeCardItem.letterColor
                    )
                    Text(
                        text = transactionTypeCardItem.typeName.uppercase(Locale.getDefault()),
                        modifier = Modifier.padding(start = 8.dp),
                        color = transactionTypeCardItem.letterColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    )
}

@Composable
fun TransactionPaymentAccountRow(paymentAccountCardItem: PaymentAccountCardItem) {
    Column(
        modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
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
                                painter = painterResource(id = paymentAccountCardItem.typeIcon),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = paymentAccountCardItem.letterColor
                            )
                            Text(
                                text = paymentAccountCardItem.typeName,
                                modifier = Modifier.padding(start = 8.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    Text(
                        text = paymentAccountCardItem.amount,
                        modifier = Modifier,
                        color = paymentAccountCardItem.letterColor,
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
                        text = paymentAccountCardItem.typeNameAccount,
                        color = paymentAccountCardItem.letterColor,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}

@Composable
fun TransactionCategoryRow(transactionCategoryCardItem: TransactionCategoryCardItem) {
    Column(
        modifier = Modifier.background(brush = transactionCategoryCardItem.gradientBrush),
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
                                    id = transactionCategoryCardItem.categoryIcon
                                ),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = transactionCategoryCardItem.letterColor
                            )
                            Text(
                                text = transactionCategoryCardItem.categoryName,
                                modifier = Modifier.padding(start = 8.dp),
                                color = transactionCategoryCardItem.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    Text(
                        text = transactionCategoryCardItem.amount,
                        modifier = Modifier,
                        color = transactionCategoryCardItem.letterColor,
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
                        text = transactionCategoryCardItem.categoryNameTransaction,
                        color = transactionCategoryCardItem.letterColor,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}