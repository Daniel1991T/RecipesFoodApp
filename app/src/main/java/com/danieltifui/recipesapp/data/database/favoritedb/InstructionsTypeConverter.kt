package com.danieltifui.recipesapp.data.database.favoritedb

import androidx.room.TypeConverter
import com.danieltifui.recipesapp.models.AnalyzedInstructions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InstructionsTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun analyzedInstructionsToString(analyzedInstructions: List<AnalyzedInstructions>): String {
        return gson.toJson(analyzedInstructions)
    }

    @TypeConverter
    fun stringToAnalyzedInstructions(data: String): List<AnalyzedInstructions> {
        val listType = object : TypeToken<AnalyzedInstructions>() {}.type
        return gson.fromJson(data, listType)
    }
}