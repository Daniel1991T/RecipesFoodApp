package com.danieltifui.recipesapp.data.database.favoritedb

import androidx.room.TypeConverter
import com.danieltifui.recipesapp.models.ExtendedIngredient
import com.danieltifui.recipesapp.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientsListTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun ingredientsListToString(ingredients: List<ExtendedIngredient>): String {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun stringToIngredientList(data: String): List<ExtendedIngredient> {
        val listType = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.fromJson(data, listType)
    }
}