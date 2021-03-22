package com.baiganov.stocksapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SuggestionConverter {

    @TypeConverter
    fun toString(suggestions: List<String>): String {
        return Gson().toJson(suggestions)
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, type)
    }
}