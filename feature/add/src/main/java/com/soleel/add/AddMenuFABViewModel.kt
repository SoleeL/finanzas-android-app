package com.soleel.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class AddUiState(
    val itemsPaymentAccount: List<PaymentAccount> = emptyList(),
    val userMessage: String? = null,
    val isPaymentAccountLoading: Boolean = false,
    val isPaymentAccountSuccess: Boolean = false,
    val isPaymentAccountEmpty: Boolean = false,
)

@HiltViewModel
open class AddMenuFABViewModel @Inject constructor(
    val repositoryLocalPaymentAccount: IPaymentAccountLocalDataSource
) : ViewModel() {

//    private val homeFlow: Flow<AddUiState> =
//        repositoryLocalPaymentAccount.getPaymentAccounts().map(transform = this::getData)

    open val addUiState: StateFlow<AddUiState> = repositoryLocalPaymentAccount
        .getPaymentAccounts()
        .map(transform = this::getData)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AddUiState(
                isPaymentAccountLoading = true
            )
        )

    fun getData(
        itemsPaymentAccount: List<PaymentAccount>
    ): AddUiState {
        return AddUiState(
            itemsPaymentAccount = itemsPaymentAccount,
            isPaymentAccountLoading = false,
            isPaymentAccountSuccess = true,
            isPaymentAccountEmpty = itemsPaymentAccount.isEmpty()
        )
    }
}