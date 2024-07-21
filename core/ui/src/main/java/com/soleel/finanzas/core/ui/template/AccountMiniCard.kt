package com.soleel.finanzas.core.ui.template

import androidx.compose.foundation.background
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
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.core.ui.uivalues.getAccountUI


@Preview
@Composable
fun AccountMiniCardPreview() {
    AccountMiniCard(
        accountUIValues = getAccountUI(
            accountTypeEnum = AccountTypeEnum.CREDIT,
            accountName = "Tarjeta de credito",
            accountAmount = "$1,000,000",
        )
    )
}

@Composable
fun AccountMiniCard(
    accountUIValues: AccountUIValues
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        content = {
            Column(
                modifier = Modifier.background(brush = accountUIValues.type.gradientBrush),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = accountUIValues.type.icon),
                                        contentDescription = "Add button.",
                                        modifier = Modifier.size(48.dp),
                                        tint = accountUIValues.type.letterColor
                                    )
                                    Text(
                                        text = accountUIValues.type.name,
                                        modifier = Modifier.padding(start = 8.dp),
                                        color = accountUIValues.type.letterColor,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            )
                            Text(
                                text = accountUIValues.amount,
                                modifier = Modifier,
                                color = accountUIValues.type.letterColor,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        content = {
                            Text(
                                modifier = Modifier,
                                text = accountUIValues.name,
                                color = accountUIValues.type.letterColor,
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