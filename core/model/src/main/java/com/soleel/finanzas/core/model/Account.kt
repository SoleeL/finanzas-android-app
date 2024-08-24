package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.SynchronizationEnum
import java.time.LocalDateTime


data class Account(
    val id: String,
    val type: AccountTypeEnum,
    val name: String,
    var amount: Int = 0,
    val createdAt: LocalDateTime,
    val updatedAt:LocalDateTime,
    val isDeleted: Boolean = false,
    val synchronization: SynchronizationEnum
)