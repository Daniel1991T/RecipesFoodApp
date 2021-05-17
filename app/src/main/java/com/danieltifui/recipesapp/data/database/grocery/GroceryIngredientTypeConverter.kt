package com.danieltifui.recipesapp.data.database.grocery

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GroceryIngredientTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun ingredientsListToString(ingredients: List<Ingredients>): String {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun stringToIngredientList(data: String): List<Ingredients> {
        val listType = object : TypeToken<List<Ingredients>>(){}.type
        return gson.fromJson(data, listType)
    }
}