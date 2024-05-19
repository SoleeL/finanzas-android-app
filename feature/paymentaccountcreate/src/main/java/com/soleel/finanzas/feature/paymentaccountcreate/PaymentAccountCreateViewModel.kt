package com.soleel.finanzas.feature.paymentaccountcreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.constants.TransactionCategoryConstant
import com.soleel.finanzas.core.common.constants.TransactionTypeConstant
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.AccountTypeValidator
import com.soleel.finanzas.domain.validation.validator.NameValidator
import com.soleel.finanzas.domain.validation.validator.PaymentAccountAmountValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PaymentAccountUiCreate(
    val type: Int = 0,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val isPaymentAccountSaved: Boolean = false
)

sealed class PaymentAccountUiEvent {
    data class NameChanged(val name: String) : PaymentAccountUiEvent()
    data class AmountChanged(val amount: String) : PaymentAccountUiEvent()
    data class TypeChanged(val accountType: Int) : PaymentAccountUiEvent()

    data object Submit : PaymentAccountUiEvent()
}

@HiltViewModel
class PaymentAccountCreateViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource
) : ViewModel() {

    var paymentAccountUiCreate by mutableStateOf(PaymentAccountUiCreate())

    private val accountTypeValidator = AccountTypeValidator()
    private val nameValidator = NameValidator()
    private val accountAmountValidator = PaymentAccountAmountValidator()

    fun onPaymentAccountCreateEventUi(event: PaymentAccountUiEvent) {
        when (event) {
            is PaymentAccountUiEvent.TypeChanged -> {
                paymentAccountUiCreate = paymentAccountUiCreate.copy(
                    type = event.accountType
                )
                validateAccountType()
            }

            is PaymentAccountUiEvent.NameChanged -> {
                paymentAccountUiCreate = paymentAccountUiCreate.copy(name = event.name)
                validateName()
            }

            is PaymentAccountUiEvent.AmountChanged -> {
                paymentAccountUiCreate =
                    paymentAccountUiCreate.copy(amount = event.amount)
                validateAmount()
            }

            is PaymentAccountUiEvent.Submit -> {
                if (validateAccountType()
                    && validateName()
                    && validateAmount()
                ) {
                    savePaymentAccount()
                }
            }
        }
    }

    private fun validateAccountType(): Boolean {
        val accountTypeResult = accountTypeValidator.execute(
            input = paymentAccountUiCreate.type
        )
        paymentAccountUiCreate = paymentAccountUiCreate.copy(
            typeError = accountTypeResult.errorMessage
        )
        return accountTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = paymentAccountUiCreate.name)
        paymentAccountUiCreate = paymentAccountUiCreate.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult =
            accountAmountValidator.execute(input = paymentAccountUiCreate.amount)
        paymentAccountUiCreate = paymentAccountUiCreate.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun savePaymentAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                val paymentAccountId = paymentAccountRepository.createPaymentAccount(
                    name = paymentAccountUiCreate.name,
                    amount = paymentAccountUiCreate.amount.toInt(),
                    accountType = paymentAccountUiCreate.type
                )

                transactionRepository.createTransaction(
                    name = "Monto inicial",
                    amount = paymentAccountUiCreate.amount.toInt(),
                    transactionType = TransactionTypeConstant.INCOME,
                    transactionCategory = TransactionCategoryConstant.INCOME_TRANSFER,
                    paymentAccountId = paymentAccountId
                )

                paymentAccountUiCreate = paymentAccountUiCreate.copy(
                    isPaymentAccountSaved = true
                )
            })
    }
}
