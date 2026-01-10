package com.soleel.finanzas.core.database.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object DateTimeConverters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): Long? {
        return date?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(epochMillis: Long?): LocalDateTime {
        return epochMillis?.let(
            block = {
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneOffset.UTC
                )
            }
        ) ?: throw IllegalArgumentException("Null epoch")
    }

}