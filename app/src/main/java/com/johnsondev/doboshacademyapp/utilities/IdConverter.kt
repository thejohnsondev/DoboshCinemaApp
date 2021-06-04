package com.johnsondev.doboshacademyapp.utilities

import androidx.room.TypeConverter

class IdConverter {

    @TypeConverter
    fun fromListToString(ids: List<Int>): String {
        return ids.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(ids: String): List<Int> {
        return ids.split(",").map { it.toInt() }
    }

}