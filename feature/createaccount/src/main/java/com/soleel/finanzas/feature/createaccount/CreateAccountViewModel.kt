package com.soleel.finanzas.feature.createaccount

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountAmount
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountType
import com.soleel.finanzas.domain.validation.validator.ValidatorName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreateAccountUi(
    val type: Int = 0,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: Int = 0,
    val amountError: Int? = null,

    val isAccountSaved: Boolean = false
)

sealed class CreateAccountEventUi {
    data class TypeChanged(val accountType: Int) : CreateAccountEventUi()
    data class NameChanged(val name: String) : CreateAccountEventUi()
    data class AmountChanged(val amount: Int) : CreateAccountEventUi()

    data object Submit : CreateAccountEventUi()
}

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    val singleEventManager: SingleEventManager
) : ViewModel() {

    var createAccountUi by mutableStateOf(CreateAccountUi())

    private val accountTypeValidator = ValidatorAccountType()
    private val nameValidator = ValidatorName()
    private val accountAmountValidator = ValidatorAccountAmount()

    fun onCreateAccountEventUi(event: CreateAccountEventUi) {
        when (event) {
            is CreateAccountEventUi.TypeChanged -> {
                createAccountUi = createAccountUi.copy(type = event.accountType)
                validateType()
            }

            is CreateAccountEventUi.NameChanged -> {
                createAccountUi = createAccountUi.copy(name = event.name)
                validateName()
            }

            is CreateAccountEventUi.AmountChanged -> {
                createAccountUi = createAccountUi.copy(amount = event.amount)
                validateAmount()
            }

            is CreateAccountEventUi.Submit -> {
                if (validateType()
                    && validateName()
                    && validateAmount()
                ) {
                    saveAccount()
                }
            }
        }
    }

    private fun validateType(): Boolean {
        val accountTypeResult = accountTypeValidator.execute(input = AccountTypeEnum.fromId(createAccountUi.type))
        createAccountUi = createAccountUi.copy(typeError = accountTypeResult.errorMessage)
        return accountTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = createAccountUi.name)
        createAccountUi = createAccountUi.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult = accountAmountValidator.execute(input = createAccountUi.amount)
        createAccountUi = createAccountUi.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    fun isValidSaveTransaction(): Boolean {
        val isValidSaveType: Boolean = 0 != createAccountUi.type && validateType()
        val isValidSaveName: Boolean = createAccountUi.name.isNotBlank() && validateName()
        val isValidSaveAmount: Boolean = 0 != createAccountUi.amount && validateAmount()

        return isValidSaveType && isValidSaveName && isValidSaveAmount
    }

    private fun saveAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                val accountId = accountRepository.createAccount(
                    name = createAccountUi.name,
                    amount = createAccountUi.amount.toInt(),
                    type = AccountTypeEnum.fromId(id = createAccountUi.type)
                )

                transactionRepository.createTransaction(
                    type = TransactionTypeEnum.INCOME.id,
                    category = TransactionCategoryEnum.INCOME_TRANSFER.id,
                    name = "Monto inicial",
                    date = System.currentTimeMillis(),
                    amount = createAccountUi.amount.toInt(),
                    accountId = accountId
                )

                createAccountUi = createAccountUi.copy(
                    isAccountSaved = true
                )
            })
    }
}
