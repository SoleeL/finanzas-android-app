package com.soleel.finanzas.feature.createtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.ValidatorAccountType
import com.soleel.finanzas.domain.validation.validator.ValidatorName
import com.soleel.finanzas.domain.validation.validator.ValidatorTransactionAmount
import com.soleel.finanzas.domain.validation.validator.ValidatorTransactionCategory
import com.soleel.finanzas.domain.validation.validator.ValidatorTransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject


data class CreateTransactionUiState(
    val account: Account = Account(
        id = "",
        type = AccountTypeEnum.CREDIT,
        name = "",
        amount = 0,
        createdAt = Date(),
        updatedAt = Date()
    ),
    val accountError: Int? = null,

    val type: Int = 0,
    val typeError: Int? = null,

    val category: Int = 0,
    val categoryError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,

    val date: Long = 0,
    val dateError: Int? = null,

    val amount: Int = 0,
    val amountError: Int? = null,

    val isSaved: Boolean = false
)

sealed class CreateTransactionUiEvent {
    data class AccountChanged(val account: Account) : CreateTransactionUiEvent()
    data class TypeChanged(val type: Int) : CreateTransactionUiEvent()
    data class CategoryChanged(val category: Int) : CreateTransactionUiEvent()
    data class NameChanged(val name: String) : CreateTransactionUiEvent()
    data class DateChanged(val date: Long) : CreateTransactionUiEvent()
    data class AmountChanged(val amount: Int) : CreateTransactionUiEvent()

    data object Submit : CreateTransactionUiEvent()
}

sealed interface AccountsUiState {
    data class Success(val accounts: List<Account>) : AccountsUiState
    data object Error : AccountsUiState
    data object Loading : AccountsUiState
}

sealed class AccountsUiEvent {
    data object Retry : AccountsUiEvent()
}

@HiltViewModel
class CreateTransactionViewModel @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    var createTransactionUiState by mutableStateOf(CreateTransactionUiState())

    private val accountValidator = ValidatorAccountType()
    private val typeValidator = ValidatorTransactionType()
    private val categoryValidator = ValidatorTransactionCategory()
    private val nameValidator = ValidatorName()
    private val amountValidator = ValidatorTransactionAmount()

    private val _accountsUiState: Flow<AccountsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = { accountUiState(accountRepository = accountRepository) })

    private fun accountUiState(
        accountRepository: IAccountLocalDataSource,
    ): Flow<AccountsUiState> {
        return accountRepository.getAccountsWithTotalAmount()
            .asResult()
            .map(transform = this::getData)
    }

    private fun getData(
        itemsAccount: Result<List<Account>>
    ): AccountsUiState {
        return when (itemsAccount) {
            is Result.Success -> AccountsUiState.Success(itemsAccount.data)
            is Result.Error -> AccountsUiState.Error
            is Result.Loading -> AccountsUiState.Loading
        }
    }

    val accountsUiState: StateFlow<AccountsUiState> = _accountsUiState
        .onStart(action = { delay(500) })
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AccountsUiState.Loading
        )

//    private fun AccountUiState(
//        AccountRepository: IAccountLocalDataSource,
//    ): Flow<AccountsUiState> {
//        return flow {
//            emit(AccountsUiState.Loading)
//
//            delay(2000)
//
//            val itemsAccount = AccountRepository.getAccountsWithTotalAmount()
//                .asResult()
//                .map {  getData(it)}
//
//            emitAll(itemsAccount)
//        }
//    }

    fun onAccountsUiEvent(event: AccountsUiEvent) {
        when (event) {
            is AccountsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

    fun onCreateTransactionUiEvent(event: CreateTransactionUiEvent) {
        when (event) {
            is CreateTransactionUiEvent.AccountChanged -> {
                // README: Campo objetivo
                createTransactionUiState = createTransactionUiState.copy(
                    account = event.account
                )

                // README: Campos afectados
                createTransactionUiState = createTransactionUiState.copy(
                    type = 0,
                    typeError = null,
                    category = 0,
                    categoryError = null
                )

                validateAccount()
            }

            is CreateTransactionUiEvent.TypeChanged -> {
                // README: Campo objetivo
                createTransactionUiState = createTransactionUiState.copy(
                    type = event.type
                )

                // README: Campos afectados
                createTransactionUiState = createTransactionUiState.copy(
                    category = 0,
                    categoryError = null
                )

                validateTransactionType()
            }

            is CreateTransactionUiEvent.CategoryChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    category = event.category
                )
                validateTransactionCategory()
            }

            is CreateTransactionUiEvent.NameChanged -> {
                createTransactionUiState = createTransactionUiState.copy(name = event.name)
                validateTransactionName()
            }

            is CreateTransactionUiEvent.DateChanged -> {
                createTransactionUiState = createTransactionUiState.copy(date = event.date)
            }

            is CreateTransactionUiEvent.AmountChanged -> {
                createTransactionUiState = createTransactionUiState.copy(amount = event.amount)
                validateTransactionAmount()
//                accountAmountRecalculate()
            }

            is CreateTransactionUiEvent.Submit -> {
                if (validateAccount()
                    && validateTransactionType()
                    && validateTransactionCategory()
                    && validateTransactionName()
                    && validateTransactionAmount()
                ) {
                    saveTransaction()
                }
            }
        }
    }

    private fun validateAccount(): Boolean {
        val accountResult = accountValidator.execute(input = createTransactionUiState.account.type)
        createTransactionUiState = createTransactionUiState.copy(
            accountError = accountResult.errorMessage
        )
        return accountResult.successful
    }

    private fun validateTransactionType(): Boolean {
        val transactionTypeResult = typeValidator.execute(
            input = createTransactionUiState.type
        )
        createTransactionUiState = createTransactionUiState.copy(
            typeError = transactionTypeResult.errorMessage
        )
        return transactionTypeResult.successful
    }

    private fun validateTransactionCategory(): Boolean {
        val transactionCategoryResult = categoryValidator.execute(
            input = createTransactionUiState.category
        )
        createTransactionUiState = createTransactionUiState.copy(
            categoryError = transactionCategoryResult.errorMessage
        )
        return transactionCategoryResult.successful
    }

    private fun validateTransactionName(): Boolean {
        val nameResult = nameValidator.execute(input = createTransactionUiState.name)
        createTransactionUiState = createTransactionUiState.copy(
            nameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateTransactionAmount(): Boolean {
        val input = Triple<Int, Int, Int>(
            first = createTransactionUiState.amount,
            second = createTransactionUiState.account.amount,
            third = createTransactionUiState.type
        )

        val amountResult = amountValidator.execute(input = input)

        createTransactionUiState = createTransactionUiState.copy(
            amountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

//    private fun accountAmountRecalculate() {
//        if (0 == initialAccountAmount) {
//            initialAccountAmount = createTransactionUiState.account.amount
//        }
//
//        createTransactionUiState.account.amount = when (createTransactionUiState.transactionType) {
//            TransactionTypeEnum.INCOME.id -> initialAccountAmount + createTransactionUiState.transactionAmount
//            TransactionTypeEnum.EXPENDITURE.id -> initialAccountAmount - createTransactionUiState.transactionAmount
//            else -> initialAccountAmount
//        }
//    }

    private fun saveTransaction() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                transactionRepository.createTransaction(
                    type = createTransactionUiState.type,
                    category = createTransactionUiState.category,
                    name = createTransactionUiState.name,
                    date = createTransactionUiState.date,
                    amount = createTransactionUiState.amount,
                    accountId = createTransactionUiState.account.id
                )

                createTransactionUiState = createTransactionUiState.copy(
                    isSaved = true
                )
            })
    }

}