package com.soleel.finanzas.core.database.converters

import androidx.room.TypeConverter
import com.soleel.finanzas.core.model.enums.AccountTypeEnum

object EnumConverters {

    @TypeConverter
    fun fromAccountType(type: AccountTypeEnum?): Int? = type?.id

    @TypeConverter
    fun toAccountType(value: Int?): AccountTypeEnum = value?.let(
        block = { AccountTypeEnum.fromId(it) }
    ) ?: throw IllegalArgumentException("Null ordinal")

}