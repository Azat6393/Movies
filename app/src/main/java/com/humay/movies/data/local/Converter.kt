package com.humay.movies.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): List<Int>? {
        value?.let {
            it.removePrefix(" ").split(",").also { list ->
                return list.map { it.toInt() }
            }
        }
        return emptyList()
    }

    @TypeConverter
    fun dateToTimestamp(list: List<Int>?): String? {
        return list?.joinToString { it.toString() }
    }
}