package com.soleel.finanzas.feature.launch.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class FailureSupplyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val code: String = savedStateHandle.get<String>("code") ?: ""
    val message: String = savedStateHandle.get<String>("message") ?: ""
}