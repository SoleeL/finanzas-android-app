package com.soleel.finanzas.data.account


//fun AccountEntity.toDto(): AccountDto {
//    return AccountDto(
//        id = this.id,
//        type = AccountTypeEnum.fromId(id = this.type),
//        name = this.name,
//        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.createdAt), ZoneId.systemDefault()),
//        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault()),
//        isDeleted = this.isDeleted,
//    )
//}
//
//fun List<AccountEntity>.toDtoList(): List<AccountDto> {
//    return this.map(transform = { it.toDto() })
//}

//fun AccountWithExpenseInfoEntity.toModel(): AccountDto {
//    return AccountDto(
//        id = this.accountEntity.id,
//        type = AccountTypeEnum.fromId(id = this.accountEntity.type),
//        name = this.accountEntity.name,
//        transactionsNumber = this.expensesNumber,
//        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.createdAt), ZoneId.systemDefault()),
//        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.updatedAt), ZoneId.systemDefault()),
//        isDeleted = this.accountEntity.isDeleted,
//
//    )
//}
//
//fun List<AccountWithExpenseInfoEntity>.toWithTotalAmountModelList(): List<AccountDto> {
//    return this.map(transform = { it.toModel() })
//}

//fun AccountDto.toEntity(): AccountEntity {
//    return AccountEntity(
//        id = this.id,
//        type = this.type.id,
//        name = this.name,
//        createdAt = System.currentTimeMillis(),
//        updatedAt = System.currentTimeMillis(),
//        isDeleted = this.isDeleted,
//        synchronization = SynchronizationEnum.PENDING.id
//    )
//}
