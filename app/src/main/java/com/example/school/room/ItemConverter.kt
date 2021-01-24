package com.example.school.room

import androidx.room.TypeConverter
import com.example.school.domain.Item
import com.google.gson.Gson

class ItemConverter {

    @TypeConverter
    fun listToJson(value: List<Item>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Item>? {

        val objects = Gson().fromJson(value, Array<Item>::class.java) as Array<Item>
        return objects.toList()
    }
}