package com.soleel.finanzas.feature.createexpense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Item
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.ui.utils.LongDevicePreview
import com.soleel.finanzas.core.ui.utils.WithFakeSystemBars
import com.soleel.finanzas.core.ui.utils.WithFakeTopAppBar
import com.soleel.finanzas.domain.formatdate.AllTransactionsGroupDateUseCase
import com.soleel.finanzas.domain.transformation.visualtransformation.CLPCurrencyVisualTransformation
import java.time.LocalDateTime
import java.util.UUID

@LongDevicePreview
@Composable
private fun ExpenseSummaryLongPreview() {
    val mockItems: List<Item> = listOf(
        Item(name = "Jabón", value = 2500f, multiply = 3f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
//        Item(name = "Detergente", value = 6500f, multiply = 1f, division = 1f, subtract = 0f),
    )

//    createExpenseViewModel.onCreateExpenseUiEvent(
//        CreateExpenseUiEvent.AccountTypeSelected(AccountTypeEnum.DEBIT)
//    )
//
//    createExpenseViewModel.onCreateExpenseUiEvent(
//        CreateExpenseUiEvent.AccountSelected(
//            Account(
//                id = UUID.randomUUID().toString(),
//                type = AccountTypeEnum.DEBIT,
//                name = "Banco falabella",
//                createdAt = LocalDateTime.now(),
//                updatedAt = LocalDateTime.now(),
//                synchronization = SynchronizationEnum.PENDING
//            )
//        )
//    )

    WithFakeSystemBars(
        content = {
            WithFakeTopAppBar(
                content = {
                    ExpenseSummaryHeader(
                        amount = mockItems.sumOf(selector = { it.value.toDouble() }).toFloat(),
                        itemCount = mockItems.size,
                        expenseTypeEnum = ExpenseTypeEnum.MARKET,
                        accountTypeEnum = AccountTypeEnum.CREDIT,
                        account = Account(
                            id = UUID.randomUUID().toString(),
                            type = AccountTypeEnum.DEBIT,
                            name = "CMR falabella",
                            createdAt = LocalDateTime.now(),
                            updatedAt = LocalDateTime.now(),
                            synchronization = SynchronizationEnum.PENDING
                        ),
                        instalments = 0,
                        date = LocalDateTime.now()
                    )
                }
            )
        }
    )
}

@Composable
fun ExpenseSummaryHeader(
    amount: Float,
    itemCount: Int,
    expenseTypeEnum: ExpenseTypeEnum? = null,
    accountTypeEnum: AccountTypeEnum? = null,
    account: Account? = null,
    instalments: Int? = null,
    date: LocalDateTime? = null,
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        content = {

            val itemsInCartTotalAmountCLP: String = CLPCurrencyVisualTransformation()
                .filter(AnnotatedString(text = amount.toInt().toString()))
                .text.toString()

            Text(
                text = buildAnnotatedString(
                    builder = {
                        append("Gasté ")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(itemsInCartTotalAmountCLP)
                            }
                        )
                    }
                ),
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (expenseTypeEnum == null) {
                Text(
                    text = buildAnnotatedString(
                        builder = {
                            append("en ")

                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold),
                                block = {
                                    if (itemCount > 1) {
                                        append("$itemCount")
                                    } else {
                                        append("un")
                                    }
                                }
                            )

                            append(if (itemCount > 1) " artículos" else " artículo")
                        }
                    ),
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            } else {
                val itemAccountString: String = if (itemCount > 1) {
                    when (expenseTypeEnum) {
                        ExpenseTypeEnum.SERVICE -> "$itemCount servicios"
                        ExpenseTypeEnum.MARKET -> "$itemCount productos"
                        ExpenseTypeEnum.ACQUISITION -> "$itemCount adquisiciones"
                        ExpenseTypeEnum.LEASURE -> "$itemCount ocios"
                        ExpenseTypeEnum.GIFT -> "$itemCount regalos"
                        ExpenseTypeEnum.TRANSFER -> "$itemCount transacciones"
                        ExpenseTypeEnum.OTHER -> "$itemCount artículos variados"
                    }
                } else {
                    when (expenseTypeEnum) {
                        ExpenseTypeEnum.SERVICE -> "un servicio"
                        ExpenseTypeEnum.MARKET -> "un producto"
                        ExpenseTypeEnum.ACQUISITION -> "una adquisición"
                        ExpenseTypeEnum.LEASURE -> "un ocio"
                        ExpenseTypeEnum.GIFT -> "un regalo"
                        ExpenseTypeEnum.TRANSFER -> "una transacción"
                        ExpenseTypeEnum.OTHER -> "un artículo variado"
                    }
                }

                val itemCountBuildString: AnnotatedString = buildAnnotatedString(
                    builder = {
                        append("en ")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(itemAccountString)
                            }
                        )
                    }
                )

                Text(
                    text = itemCountBuildString,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (accountTypeEnum != null && account == null) {
                Spacer(modifier = Modifier.height(8.dp))

                val accountTypeBuildString: AnnotatedString = buildAnnotatedString(
                    builder = {
                        append("que pague con ")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(accountTypeEnum.value)
                            }
                        )
                    }
                )

                Text(
                    text = accountTypeBuildString,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (accountTypeEnum != null && account != null) {
                Spacer(modifier = Modifier.height(8.dp))

                val accountWithTypeBuildString: AnnotatedString = buildAnnotatedString(
                    builder = {
                        append("que pague con ")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(account.name)
                            }
                        )
                        append(" (")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(accountTypeEnum.value)
                            }
                        )
                        append(")")
                    }
                )

                Text(
                    text = accountWithTypeBuildString,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            if (instalments != null) {
                Spacer(modifier = Modifier.height(8.dp))
                val instalmentsBuildString: AnnotatedString = buildAnnotatedString(
                    builder = {
                        if (instalments > 0) {
                            append("en ")
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold),
                                block = {
                                    append(instalments.toString())
                                }
                            )
                            append(" cuotas")
                        } else {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold),
                                block = {
                                    append("sin")
                                }
                            )
                            append(" cuotas")
                        }
                    }
                )

                Text(
                    text = instalmentsBuildString,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            if (date != null) {
                Spacer(modifier = Modifier.height(8.dp))

                val accountWithTypeBuildString: AnnotatedString = buildAnnotatedString(
                    builder = {
                        append("el ")
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            block = {
                                append(AllTransactionsGroupDateUseCase(date.toLocalDate()))
                            }
                        )
                    }
                )

                Text(
                    text = accountWithTypeBuildString,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    )
}
