package com.soleel.add

import androidx.lifecycle.viewModelScope
import com.soleel.paymentaccount.PaymentAccountRepositoryMock
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AddMenuFABViewModelMock : AddMenuFABViewModel(PaymentAccountRepositoryMock()) {

    override val addUiState: StateFlow<AddUiState> = repositoryLocalPaymentAccount
        .getPaymentAccounts()
        .map(transform = this::getData)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AddUiState(
                isPaymentAccountLoading = true
            )
        )
}
