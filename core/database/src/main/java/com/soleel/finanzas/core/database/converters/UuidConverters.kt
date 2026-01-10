package com.soleel.finanzas.core.database.converters

import androidx.room.TypeConverter
import java.util.UUID

object UuidConverters {

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun toUUID(str: String?): UUID? = str?.let { UUID.fromString(it) }

}