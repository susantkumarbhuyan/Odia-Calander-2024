package com.odiacalander.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListString(value: String): Map<String, List<String>> {
        val mapType = object : TypeToken<Map<String, List<String>>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun toListString(value: Map<String, List<String>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromMapString(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun toMapString(map: Map<String, String>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun fromIntString(value: String): Map<String, Int> {
        val type = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toIntString(value: Map<String, Int>): String {
        return Gson().toJson(value)
    }
}