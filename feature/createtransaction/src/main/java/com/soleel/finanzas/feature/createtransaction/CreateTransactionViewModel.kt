package com.soleel.finanzas.feature.createtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
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
import java.util.Date
import javax.inject.Inject


data class CreateTransactionUiState(
    val account: Account = Account(
        id = "",
        name = "",
        amount = 0,
        createAt = Date(),
        updatedAt = Date(),
        type = AccountTypeEnum.CREDIT
    ),
    val accountError: Int? = null,

    val transactionType: Int = 0,
    val transactionTypeError: Int? = null,

    val transactionCategory: Int = 0,
    val transactionCategoryError: Int? = null,

    val transactionName: String = "",
    val transactionNameError: Int? = null,

    val transactionAmount: Int = 0,
    val transactionAmountError: Int? = null,

    val isTransactionSaved: Boolean = false
)

sealed class CreateTransactionUiEvent {
    data class AccountChanged(val account: Account) : CreateTransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : CreateTransactionUiEvent()
    data class TransactionCategoryChanged(val transactionCategory: Int) : CreateTransactionUiEvent()
    data class TransactionNameChanged(val transactionName: String) : CreateTransactionUiEvent()
    data class TransactionAmountChanged(val transactionAmount: Int) : CreateTransactionUiEvent()

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
    private var initialAccountAmount = 0

    private val accountValidator = ValidatorAccountType()
    private val transactionTypeValidator = ValidatorTransactionType()
    private val transactionCategoryValidator = ValidatorTransactionCategory()
    private val transactionNameValidator = ValidatorName()
    private val transactionAmountValidator = ValidatorTransactionAmount()

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
                    transactionType = 0,
                    transactionTypeError = null,
                    transactionCategory = 0,
                    transactionCategoryError = null
                )

                validateAccount()
            }

            is CreateTransactionUiEvent.TransactionTypeChanged -> {
                // README: Campo objetivo
                createTransactionUiState = createTransactionUiState.copy(
                    transactionType = event.transactionType
                )

                // README: Campos afectados
                createTransactionUiState = createTransactionUiState.copy(
                    transactionCategory = 0,
                    transactionCategoryError = null
                )

                validateTransactionType()
            }

            is CreateTransactionUiEvent.TransactionCategoryChanged -> {
                createTransactionUiState = createTransactionUiState.copy(
                    transactionCategory = event.transactionCategory
                )
                validateTransactionCategory()
            }

            is CreateTransactionUiEvent.TransactionNameChanged -> {
                createTransactionUiState =
                    createTransactionUiState.copy(transactionName = event.transactionName)
                validateTransactionName()
            }

            is CreateTransactionUiEvent.TransactionAmountChanged -> {
                createTransactionUiState = createTransactionUiState.copy(transactionAmount = event.transactionAmount)
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
        val transactionTypeResult = transactionTypeValidator.execute(
            input = createTransactionUiState.transactionType
        )
        createTransactionUiState = createTransactionUiState.copy(
            transactionTypeError = transactionTypeResult.errorMessage
        )
        return transactionTypeResult.successful
    }

    private fun validateTransactionCategory(): Boolean {
        val transactionCategoryResult = transactionCategoryValidator.execute(
            input = createTransactionUiState.transactionCategory
        )
        createTransactionUiState = createTransactionUiState.copy(
            transactionCategoryError = transactionCategoryResult.errorMessage
        )
        return transactionCategoryResult.successful
    }

    private fun validateTransactionName(): Boolean {
        val nameResult =
            transactionNameValidator.execute(input = createTransactionUiState.transactionName)
        createTransactionUiState = createTransactionUiState.copy(
            transactionNameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateTransactionAmount(): Boolean {
        val input = Triple<Int, Int, Int>(
            first = createTransactionUiState.transactionAmount,
            second = createTransactionUiState.account.amount,
            third = createTransactionUiState.transactionType
        )

        val amountResult = transactionAmountValidator.execute(input = input)

        createTransactionUiState = createTransactionUiState.copy(
            transactionAmountError = amountResult.errorMessage
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
                    name = createTransactionUiState.transactionName,
                    amount = createTransactionUiState.transactionAmount,
                    transactionType = createTransactionUiState.transactionType,
                    transactionCategory = createTransactionUiState.transactionCategory,
                    accountId = createTransactionUiState.account.id
                )

                createTransactionUiState = createTransactionUiState.copy(
                    isTransactionSaved = true
                )
            })
    }

}