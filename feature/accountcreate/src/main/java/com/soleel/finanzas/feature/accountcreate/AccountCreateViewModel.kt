package com.soleel.finanzas.feature.accountcreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.AccountAmountValidator
import com.soleel.finanzas.domain.validation.validator.AccountTypeValidator
import com.soleel.finanzas.domain.validation.validator.NameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AccountUiCreate(
    val type: Int = 0,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val amount: String = "",
    val amountError: Int? = null,

    val isAccountSaved: Boolean = false
)

sealed class AccountUiEvent {
    data class NameChanged(val name: String) : AccountUiEvent()
    data class AmountChanged(val amount: String) : AccountUiEvent()
    data class TypeChanged(val accountType: Int) : AccountUiEvent()

    data object Submit : AccountUiEvent()
}

@HiltViewModel
class AccountCreateViewModel @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource
) : ViewModel() {

    var accountUiCreate by mutableStateOf(AccountUiCreate())

    private val accountTypeValidator = AccountTypeValidator()
    private val nameValidator = NameValidator()
    private val accountAmountValidator = AccountAmountValidator()

    fun onAccountCreateEventUi(event: AccountUiEvent) {
        when (event) {
            is AccountUiEvent.TypeChanged -> {
                accountUiCreate = accountUiCreate.copy(type = event.accountType)
                validateAccountType()
            }

            is AccountUiEvent.NameChanged -> {
                accountUiCreate = accountUiCreate.copy(name = event.name)
                validateName()
            }

            is AccountUiEvent.AmountChanged -> {
                accountUiCreate = accountUiCreate.copy(amount = event.amount)
                validateAmount()
            }

            is AccountUiEvent.Submit -> {
                if (validateAccountType()
                    && validateName()
                    && validateAmount()
                ) {
                    saveAccount()
                }
            }
        }
    }

    private fun validateAccountType(): Boolean {
        val accountTypeResult = accountTypeValidator.execute(input = AccountTypeEnum.fromId(accountUiCreate.type))
        accountUiCreate = accountUiCreate.copy(typeError = accountTypeResult.errorMessage)
        return accountTypeResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = accountUiCreate.name)
        accountUiCreate = accountUiCreate.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateAmount(): Boolean {
        val amountResult = accountAmountValidator.execute(input = accountUiCreate.amount)
        accountUiCreate = accountUiCreate.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun saveAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                val accountId = accountRepository.createAccount(
                    name = accountUiCreate.name,
                    amount = accountUiCreate.amount.toInt(),
                    type = AccountTypeEnum.fromId(id = accountUiCreate.type)
                )

                transactionRepository.createTransaction(
                    name = "Monto inicial",
                    amount = accountUiCreate.amount.toInt(),
                    transactionType = TransactionTypeEnum.INCOME.id,
                    transactionCategory = TransactionCategoryEnum.INCOME_TRANSFER.id,
                    accountId = accountId
                )

                accountUiCreate = accountUiCreate.copy(
                    isAccountSaved = true
                )
            })
    }
}
