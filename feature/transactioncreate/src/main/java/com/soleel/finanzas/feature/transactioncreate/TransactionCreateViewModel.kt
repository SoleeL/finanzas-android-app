package com.soleel.finanzas.feature.transactioncreate

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
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.NameValidator
import com.soleel.finanzas.domain.validation.validator.AccountTypeValidator
import com.soleel.finanzas.domain.validation.validator.TransactionAmountValidator
import com.soleel.finanzas.domain.validation.validator.TransactionCategoryValidator
import com.soleel.finanzas.domain.validation.validator.TransactionTypeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


data class TransactionUiCreate(
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

sealed class TransactionUiEvent {
    data class AccountChanged(val Account: Account) : TransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : TransactionUiEvent()
    data class TransactionCategoryChanged(val transactionCategory: Int) : TransactionUiEvent()
    data class TransactionNameChanged(val transactionName: String) : TransactionUiEvent()
    data class TransactionAmountChanged(val transactionAmount: Int) : TransactionUiEvent()

    data object Submit : TransactionUiEvent()
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
class TransactionCreateViewModel @Inject constructor(
    private val AccountRepository: IAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    var transactionUiCreate by mutableStateOf(TransactionUiCreate())
    private var initialAccountAmount = 0

    private val accountValidator = AccountTypeValidator()
    private val transactionTypeValidator = TransactionTypeValidator()
    private val transactionCategoryValidator = TransactionCategoryValidator()
    private val transactionNameValidator = NameValidator()
    private val transactionAmountValidator = TransactionAmountValidator()

    private val _accountsUiState: Flow<AccountsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = {
            accountUiState(AccountRepository = AccountRepository)
        })

    val accountsUiState: StateFlow<AccountsUiState> = _accountsUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = AccountsUiState.Loading
    )

    private fun accountUiState(
        AccountRepository: IAccountLocalDataSource,
    ): Flow<AccountsUiState> {
        return AccountRepository.getAccountsWithTotalAmount()
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

    fun onTransactionCreateUiEvent(event: TransactionUiEvent) {
        when (event) {
            is TransactionUiEvent.AccountChanged -> {
                // README: Campo objetivo
                transactionUiCreate = transactionUiCreate.copy(
                    account = event.Account
                )

                // README: Campos afectados
                transactionUiCreate = transactionUiCreate.copy(
                    transactionType = 0,
                    transactionTypeError = null,
                    transactionCategory = 0,
                    transactionCategoryError = null
                )

                validateAccount()
            }

            is TransactionUiEvent.TransactionTypeChanged -> {
                // README: Campo objetivo
                transactionUiCreate = transactionUiCreate.copy(
                    transactionType = event.transactionType
                )

                // README: Campos afectados
                transactionUiCreate = transactionUiCreate.copy(
                    transactionCategory = 0,
                    transactionCategoryError = null
                )

                validateTransactionType()
            }

            is TransactionUiEvent.TransactionCategoryChanged -> {
                transactionUiCreate = transactionUiCreate.copy(
                    transactionCategory = event.transactionCategory
                )
                validateTransactionCategory()
            }

            is TransactionUiEvent.TransactionNameChanged -> {
                transactionUiCreate =
                    transactionUiCreate.copy(transactionName = event.transactionName)
                validateTransactionName()
            }

            is TransactionUiEvent.TransactionAmountChanged -> {
                transactionUiCreate = transactionUiCreate.copy(transactionAmount = event.transactionAmount)
                validateTransactionAmount()
                AccountAmountRecalculate()
            }

            is TransactionUiEvent.Submit -> {
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
        val accountResult = accountValidator.execute(input = transactionUiCreate.account.type)
        transactionUiCreate = transactionUiCreate.copy(
            accountError = accountResult.errorMessage
        )
        return accountResult.successful
    }

    private fun validateTransactionType(): Boolean {
        val transactionTypeResult = transactionTypeValidator.execute(
            input = transactionUiCreate.transactionType
        )
        transactionUiCreate = transactionUiCreate.copy(
            transactionTypeError = transactionTypeResult.errorMessage
        )
        return transactionTypeResult.successful
    }

    private fun validateTransactionCategory(): Boolean {
        val transactionCategoryResult = transactionCategoryValidator.execute(
            input = transactionUiCreate.transactionCategory
        )
        transactionUiCreate = transactionUiCreate.copy(
            transactionCategoryError = transactionCategoryResult.errorMessage
        )
        return transactionCategoryResult.successful
    }

    private fun validateTransactionName(): Boolean {
        val nameResult =
            transactionNameValidator.execute(input = transactionUiCreate.transactionName)
        transactionUiCreate = transactionUiCreate.copy(
            transactionNameError = nameResult.errorMessage
        )
        return nameResult.successful
    }

    private fun validateTransactionAmount(): Boolean {
        val input = Triple<Int, Int, Int>(
            first = transactionUiCreate.transactionAmount,
            second = transactionUiCreate.account.amount,
            third = transactionUiCreate.transactionType
        )

        val amountResult = transactionAmountValidator.execute(input = input)

        transactionUiCreate = transactionUiCreate.copy(
            transactionAmountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun AccountAmountRecalculate() {
        if (0 == initialAccountAmount) {
            initialAccountAmount = transactionUiCreate.account.amount
        }

        transactionUiCreate.account.amount = when (transactionUiCreate.transactionType) {
            TransactionTypeEnum.INCOME.id -> initialAccountAmount + transactionUiCreate.transactionAmount
            TransactionTypeEnum.EXPENDITURE.id -> initialAccountAmount - transactionUiCreate.transactionAmount
            else -> initialAccountAmount
        }
    }

    private fun saveTransaction() {
        viewModelScope.launch(
            context = Dispatchers.IO,
            block = {
                transactionRepository.createTransaction(
                    name = transactionUiCreate.transactionName,
                    amount = transactionUiCreate.transactionAmount,
                    transactionType = transactionUiCreate.transactionType,
                    transactionCategory = transactionUiCreate.transactionCategory,
                    accountId = transactionUiCreate.account.id
                )

                transactionUiCreate = transactionUiCreate.copy(
                    isTransactionSaved = true
                )
            })
    }

}

