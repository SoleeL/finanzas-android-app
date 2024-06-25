package com.soleel.finanzas.feature.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.core.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class AddUiState(
    val itemsAccount: List<Account> = emptyList(),
    val userMessage: String? = null,
    val isAccountLoading: Boolean = false,
    val isAccountSuccess: Boolean = false,
    val isAccountEmpty: Boolean = false,
)

@HiltViewModel
open class AddMenuFABViewModel @Inject constructor(
    val repositoryLocalAccount: IAccountLocalDataSource
) : ViewModel() {

//    private val homeFlow: Flow<AddUiState> =
//        repositoryLocalAccount.getAccounts().map(transform = this::getData)

    open val addUiState: StateFlow<AddUiState> = repositoryLocalAccount
        .getAccounts()
        .map(transform = this::getData)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AddUiState(
                isAccountLoading = true
            )
        )

    fun getData(
        itemsAccount: List<Account>
    ): AddUiState {
        return AddUiState(
            itemsAccount = itemsAccount,
            isAccountLoading = false,
            isAccountSuccess = true,
            isAccountEmpty = itemsAccount.isEmpty()
        )
    }
}