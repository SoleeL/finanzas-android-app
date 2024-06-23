package com.soleel.finanzas.feature.transactioncreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.common.result.Result
import com.soleel.finanzas.core.common.result.asResult
import com.soleel.finanzas.core.common.retryflow.RetryableFlowTrigger
import com.soleel.finanzas.core.common.retryflow.retryableFlow
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.core.model.PaymentAccount
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.validation.validator.NameValidator
import com.soleel.finanzas.domain.validation.validator.PaymentAccountTypeValidator
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
import javax.inject.Inject


data class TransactionUiCreate(
    val paymentAccount: com.soleel.finanzas.core.model.PaymentAccount = com.soleel.finanzas.core.model.PaymentAccount(
        id = "", name = "", amount = 0, createAt = 0, updatedAt = 0, accountType = 0
    ),
    val paymentAccountError: Int? = null,

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
    data class PaymentAccountChanged(val paymentAccount: com.soleel.finanzas.core.model.PaymentAccount) : TransactionUiEvent()
    data class TransactionTypeChanged(val transactionType: Int) : TransactionUiEvent()
    data class TransactionCategoryChanged(val transactionCategory: Int) : TransactionUiEvent()
    data class TransactionNameChanged(val transactionName: String) : TransactionUiEvent()
    data class TransactionAmountChanged(val transactionAmount: Int) : TransactionUiEvent()

    data object Submit : TransactionUiEvent()
}

sealed interface PaymentAccountsUiState {
    data class Success(val paymentAccounts: List<com.soleel.finanzas.core.model.PaymentAccount>) : PaymentAccountsUiState
    data object Error : PaymentAccountsUiState
    data object Loading : PaymentAccountsUiState
}

sealed class PaymentAccountsUiEvent {
    data object Retry : PaymentAccountsUiEvent()
}


@HiltViewModel
class TransactionCreateViewModel @Inject constructor(
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
    private val retryableFlowTrigger: RetryableFlowTrigger
) : ViewModel() {

    var transactionUiCreate by mutableStateOf(TransactionUiCreate())
    private var initialPaymentAccountAmount = 0

    private val paymentAccountValidator = PaymentAccountTypeValidator()
    private val transactionTypeValidator = TransactionTypeValidator()
    private val transactionCategoryValidator = TransactionCategoryValidator()
    private val transactionNameValidator = NameValidator()
    private val transactionAmountValidator = TransactionAmountValidator()

    private val _paymentAccountsUiState: Flow<PaymentAccountsUiState> = retryableFlowTrigger
        .retryableFlow(flowProvider = {
            paymentAccountUiState(paymentAccountRepository = paymentAccountRepository)
        })

    val paymentAccountsUiState: StateFlow<PaymentAccountsUiState> = _paymentAccountsUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = PaymentAccountsUiState.Loading
    )

    private fun paymentAccountUiState(
        paymentAccountRepository: IPaymentAccountLocalDataSource,
    ): Flow<PaymentAccountsUiState> {
        return paymentAccountRepository.getPaymentAccountsWithTotalAmount()
            .asResult()
            .map(transform = this::getData)
    }

    private fun getData(
        itemsPaymentAccount: Result<List<com.soleel.finanzas.core.model.PaymentAccount>>
    ): PaymentAccountsUiState {
        return when (itemsPaymentAccount) {
            is Result.Success -> PaymentAccountsUiState.Success(itemsPaymentAccount.data)
            is Result.Error -> PaymentAccountsUiState.Error
            is Result.Loading -> PaymentAccountsUiState.Loading
        }
    }


//    private fun paymentAccountUiState(
//        paymentAccountRepository: IPaymentAccountLocalDataSource,
//    ): Flow<PaymentAccountsUiState> {
//        return flow {
//            emit(PaymentAccountsUiState.Loading)
//
//            delay(2000)
//
//            val itemsPaymentAccount = paymentAccountRepository.getPaymentAccountsWithTotalAmount()
//                .asResult()
//                .map {  getData(it)}
//
//            emitAll(itemsPaymentAccount)
//        }
//    }

    fun onPaymentAccountsUiEvent(event: PaymentAccountsUiEvent) {
        when (event) {
            is PaymentAccountsUiEvent.Retry -> {
                retryableFlowTrigger.retry()
            }
        }
    }

    fun onTransactionCreateUiEvent(event: TransactionUiEvent) {
        when (event) {
            is TransactionUiEvent.PaymentAccountChanged -> {
                // README: Campo objetivo
                transactionUiCreate = transactionUiCreate.copy(
                    paymentAccount = event.paymentAccount
                )

                // README: Campos afectados
                transactionUiCreate = transactionUiCreate.copy(
                    transactionType = 0,
                    transactionTypeError = null,
                    transactionCategory = 0,
                    transactionCategoryError = null
                )

                validatePaymentAccount()
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
                paymentAccountAmountRecalculate()
            }

            is TransactionUiEvent.Submit -> {
                if (validatePaymentAccount()
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

    private fun validatePaymentAccount(): Boolean {
        val paymentAccountResult = paymentAccountValidator.execute(
            input = transactionUiCreate.paymentAccount
        )
        transactionUiCreate = transactionUiCreate.copy(
            paymentAccountError = paymentAccountResult.errorMessage
        )
        return paymentAccountResult.successful
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
            second = transactionUiCreate.paymentAccount.amount,
            third = transactionUiCreate.transactionType
        )

        val amountResult = transactionAmountValidator.execute(input = input)

        transactionUiCreate = transactionUiCreate.copy(
            transactionAmountError = amountResult.errorMessage
        )
        return amountResult.successful
    }

    private fun paymentAccountAmountRecalculate() {
        if (0 == initialPaymentAccountAmount) {
            initialPaymentAccountAmount = transactionUiCreate.paymentAccount.amount
        }

        transactionUiCreate.paymentAccount.amount = when (transactionUiCreate.transactionType) {
            TransactionTypeEnum.INCOME.id -> initialPaymentAccountAmount + transactionUiCreate.transactionAmount
            TransactionTypeEnum.EXPENDITURE.id -> initialPaymentAccountAmount - transactionUiCreate.transactionAmount
            else -> initialPaymentAccountAmount
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
                    paymentAccountId = transactionUiCreate.paymentAccount.id
                )

                transactionUiCreate = transactionUiCreate.copy(
                    isTransactionSaved = true
                )
            })
    }

}

