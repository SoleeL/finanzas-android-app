package com.soleel.finanzas.feature.createaccount

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager
import com.soleel.finanzas.core.model.base.AccountDto
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountAmount
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountType
import com.soleel.finanzas.domain.validation.validator.ValidatorName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


data class CreateAccountUi(
    val type: AccountTypeEnum = AccountTypeEnum.CASH,
    val typeError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val isAccountSaved: Boolean = false
)

sealed class CreateAccountEventUi {
    data class TypeChanged(val accountType: AccountTypeEnum) : CreateAccountEventUi()
    data class NameChanged(val name: String) : CreateAccountEventUi()
    data object Submit : CreateAccountEventUi()
}

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountRepository: IAccountRepository,
    val singleEventManager: SingleEventManager
) : ViewModel() {

    var createAccountUi by mutableStateOf(CreateAccountUi())

    private val accountTypeValidator: ValidatorAccountType = ValidatorAccountType()
    private val nameValidator: ValidatorName = ValidatorName()
    private val accountAmountValidator: ValidatorAccountAmount = ValidatorAccountAmount()

    fun onCreateAccountEventUi(event: CreateAccountEventUi) {
        when (event) {
            is CreateAccountEventUi.TypeChanged -> {
                createAccountUi = createAccountUi.copy(type = event.accountType)
//                validateType()
            }

            is CreateAccountEventUi.NameChanged -> {
                createAccountUi = createAccountUi.copy(name = event.name)
                validateName()
            }

            is CreateAccountEventUi.Submit -> {
                saveAccount()
            }
        }
    }

//    private fun validateType(): Boolean {
//        val accountTypeResult: ResultValidation = accountTypeValidator.execute(
//            input = AccountTypeEnum.fromId(createAccountUi.type)
//        )
//        createAccountUi = createAccountUi.copy(typeError = accountTypeResult.errorMessage)
//        return accountTypeResult.successful
//    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(input = createAccountUi.name)
        createAccountUi = createAccountUi.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun saveAccount() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {

                val account: AccountDto = AccountDto(
                    type = createAccountUi.type,
                    issue = createAccountUi.issue,
                    fee = createAccountUi.fee,
                    creditLimit = createAccountUi.creditLimit,
                    interestRate = createAccountUi.interestRate,
                    interestFreeInstallments = createAccountUi.interestFreeInstallments,
                    billingDay = createAccountUi.billingDay,
                    dueDay = createAccountUi.dueDay,
                    name = createAccountUi.name
                )

                val accountId: UUID = accountRepository.createAccount(account)

                createAccountUi = createAccountUi.copy(
                    isAccountSaved = true
                )
            }
        )
    }
}
