package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.model.base.AccountDto
import com.soleel.finanzas.core.model.convertions.deviceToUtc
import com.soleel.finanzas.core.model.convertions.utcToDevice
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID


fun AccountDto.toEntity(): AccountEntity {
    val nowUtc: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)

    return AccountEntity(
        id = this.id ?: UUID.randomUUID(),
        type = this.type,
        issue = this.issue,
        fee = this.fee,
        creditLimit = this.creditLimit,
        interestRate = this.interestRate,
        interestFreeInstallments = this.interestFreeInstallments,
        billingDay = this.billingDay,
        dueDay = this.dueDay,
        name = this.name,
        createdAt = this.createdAt?.let(block = { it.deviceToUtc() }) ?: nowUtc,
        updatedAt = this.updatedAt?.let(block = { it.deviceToUtc() }) ?: nowUtc,
        isDeleted = this.isDeleted,
    )
}

fun AccountEntity.toDto(): AccountDto {
    return AccountDto(
        id = this.id,
        type = this.type,
        issue = this.issue,
        fee = this.fee,
        creditLimit = this.creditLimit,
        interestRate = this.interestRate,
        interestFreeInstallments = this.interestFreeInstallments,
        billingDay = this.billingDay,
        dueDay = this.dueDay,
        name = this.name,
        createdAt = this.createdAt.utcToDevice(),
        updatedAt = this.updatedAt.utcToDevice(),
        isDeleted = this.isDeleted,
    )
}

fun List<AccountEntity>.toDtoList(): List<AccountDto> {
    return this.map(transform = { it.toDto() })
}