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
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.theme.CreditGradientColor1
import com.soleel.finanzas.core.ui.theme.CreditGradientColor2
import com.soleel.finanzas.core.ui.theme.CreditLetterColor
import com.soleel.finanzas.core.ui.util.PaymentAccountCardItem
import com.soleel.finanzas.core.ui.util.getCardLinearGradient

@Preview
@Composable
fun PaymentAccountCardPreview() {
    PaymentAccountCard(
        paymentAccountCardItem = PaymentAccountCardItem(
            type = PaymentAccountTypeConstant.CREDIT,
            typeName = "CREDITO",
            typeNameAccount = "Tarjeta de credito",
            typeIcon = R.drawable.ic_credit,
            amount = "$1,000,000",
            letterColor = CreditLetterColor,
            gradientBrush = getCardLinearGradient(
                            CreditGradientColor1,
                            CreditGradientColor2
                        )
        ),
        onClick = {},
        onClickEnable = false
    )
}

@Composable
fun PaymentAccountCard(
    paymentAccountCardItem: PaymentAccountCardItem,
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
            Column(
                modifier = Modifier.background(brush = paymentAccountCardItem.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = paymentAccountCardItem.typeName,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = paymentAccountCardItem.amount,
                                modifier = Modifier.padding(16.dp),
                                color = paymentAccountCardItem.letterColor,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .padding(16.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = paymentAccountCardItem.typeIcon),
                                contentDescription = "Add button.",
                                modifier = Modifier.size(48.dp),
                                tint = paymentAccountCardItem.letterColor
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                content = {
                                    Text(
                                        modifier = Modifier,
                                        text = "**** **** **** 3456",
                                        color = paymentAccountCardItem.letterColor,
                                        style = MaterialTheme.typography.headlineLarge,
                                        textAlign = TextAlign.Center
                                    )
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
            )
        }
    )
}