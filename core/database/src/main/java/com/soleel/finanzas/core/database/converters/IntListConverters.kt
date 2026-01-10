package com.soleel.finanzas.core.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object IntListConverters {

    @TypeConverter
    fun fromIntList(list: List<Int>?): String? = list?.let { Gson().toJson(it) }

    @TypeConverter
    fun toIntList(json: String?): List<Int> = json?.let {
        val type = object : TypeToken<List<Int>>() {}.type
        Gson().fromJson(it, type)
    } ?: emptyList()

}