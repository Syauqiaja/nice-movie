package com.syauqi.watcheez.core.data.source.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromListInt(value: List<Int>): String{
        return gson.toJson(value)
    }
    @TypeConverter
    fun toListInt(value: String): List<Int>{
        val listType = object :TypeToken<List<Int>>(){}.type
        return gson.fromJson(value, listType)
    }
}