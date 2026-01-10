package com.soleel.finanzas.core.model.base

import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import java.time.LocalDateTime
import java.util.UUID


// README: El proposito de estos DTOs es permitir la inexistencia de datos
//  Por ejemplo, antes de que la cuenta sea insertada a la DB local, esta no debe tener
//  identificador, createAt o updateAt
//  IMPORTANTE: Lamentablemente, se debe aceptar la existencia de estos valores nullables y validar
//      su existena en la capa :domain y :feature
//  IMPORTANTE: LocalDateTime como createdAt y updatedAt son fecha/hora que aplican la zona horaria del sistema
data class ExpenseDto(
    var id: UUID? = null,
    val type: ExpenseTypeEnum,
    val name: String,
    val date: LocalDateTime,
    val amount: Int,
    val accountId: UUID,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    val isDeleted: Boolean = false
)