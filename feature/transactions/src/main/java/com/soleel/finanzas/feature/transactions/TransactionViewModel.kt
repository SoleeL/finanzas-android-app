package com.soleel.finanzas.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.data.transaction.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class HomeUiState(
    val itemsTransaction: List<Transaction> = emptyList(),
    val itemsPaymentAccount: List<PaymentAccount> = emptyList(),

    val userMessage: String? = null,
    val isPaymentAccountLoading: Boolean = false,
    val isPaymentAccountSuccess: Boolean = false,
    val isTransactionLoading: Boolean = false,
    val isTransactionSuccess: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryLocalTransaction: ITransactionLocalDataSource,
    private val repositoryLocalPaymentAccount: IPaymentAccountLocalDataSource
) : ViewModel() {

    private val homeFlow: Flow<HomeUiState> = combine(
        flow = repositoryLocalTransaction.getTransactions(),
        flow2 = repositoryLocalPaymentAccount.getPaymentAccounts(),
        transform = this::getData
    )

    val homeUiState: StateFlow<HomeUiState> = homeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(
            isPaymentAccountLoading = true,
            isTransactionLoading = true
        )
    )

    private fun getData(
        itemsTransaction: List<Transaction>,
        itemsPaymentAccount: List<PaymentAccount>
    ): HomeUiState {
        return HomeUiState(
            itemsTransaction = itemsTransaction,
            itemsPaymentAccount = itemsPaymentAccount,
            isPaymentAccountLoading = false,
            isPaymentAccountSuccess = true,
            isTransactionLoading = false,
            isTransactionSuccess = true
        )
    }

}
