package com.soleel.finanzas.core.database.converters

import androidx.room.TypeConverter
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum


object EnumConverters {

    @TypeConverter
    fun fromAccountType(type: AccountTypeEnum?): Int? = type?.id

    @TypeConverter
    fun toAccountType(value: Int?): AccountTypeEnum = value?.let(
        block = { AccountTypeEnum.fromId(it) }
    ) ?: throw IllegalArgumentException("Null ordinal")

    @TypeConverter
    fun fromExpenseType(type: ExpenseTypeEnum?): Int? = type?.id

    @TypeConverter
    fun toExpenseTypeEnum(value: Int?): ExpenseTypeEnum = value?.let(
        block = { ExpenseTypeEnum.fromId(it) }
    ) ?: throw IllegalArgumentException("Null ordinal")

}