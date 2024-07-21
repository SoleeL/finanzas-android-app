package com.soleel.finanzas.feature.add

import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.data.account.AccountRepositoryMock
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AddMenuFABViewModelMock : AddMenuFABViewModel(AccountRepositoryMock()) {

    override val addUiState: StateFlow<AddUiState> = repositoryLocalAccount
        .getAccounts()
        .map(transform = this::getData)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AddUiState(
                isAccountLoading = true
            )
        )
}
