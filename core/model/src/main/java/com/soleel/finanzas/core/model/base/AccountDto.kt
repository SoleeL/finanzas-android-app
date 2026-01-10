package com.soleel.finanzas.core.model.base

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import java.time.LocalDateTime


//data class AccountDto(
//    val id: String,
//
//    val type: AccountTypeEnum,
//
//    val issue: String, // README: Emisor de la tarjeta/medio de pago
//    val fee: Int, // README: Es la comision por uso de la tarjeta
//
//    val creditLimit: Int, // README: Es el cupo de la tarjeta
//    val interestRate: Float, // README: Es la tasa de interes vigente
//    val interestFreeInstallments: List<Int>, // README: Es el listado de cuotas sin intereses
//    val billingDay: Int, // README: Es el dia de facturacion de la tarjeta
//    val dueDay: Int, // README: Es el dia limite de pago de la tarjeta
//
//    val name: String,
//    val createdAt: LocalDateTime,
//    val updatedAt: LocalDateTime,
//    val isDeleted: Boolean = false
//)