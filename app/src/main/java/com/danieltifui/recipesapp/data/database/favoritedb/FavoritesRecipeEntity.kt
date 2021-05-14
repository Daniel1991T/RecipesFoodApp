package com.danieltifui.recipesapp.data.database.favoritedb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danieltifui.recipesapp.models.AnalyzedInstructions
import com.danieltifui.recipesapp.models.ExtendedIngredient
import com.danieltifui.recipesapp.untils.Constants.Companion.FAVORITES_RECIPE_TABLE
import kotlinx.parcelize.RawValue

data class FavoritesRecipeEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    val sourceName: String?,
    val sourceUrl: String?,
    val summary: String?,
    val title: String,
    val image: String?,
    @ColumnInfo(name = "aggregate_likes") val aggregateLikes: Int?,
    @ColumnInfo(name = "ready_in_minutes") val readyInMinutes: Int,
    val cheap: Boolean? = false,
    @ColumnInfo(name = "dairy_free") val dairyFree: Boolean? = false,
    @ColumnInfo(name = "gluten_free") val glutenFree: Boolean? = false,
    val vegan: Boolean? = false,
    val vegetarian: Boolean? = false,
    val veryHealthy: Boolean? = false,
    @ColumnInfo(name = "analyzed_instructions") val analyzedInstructions: @RawValue List<AnalyzedInstructions>?,
    @ColumnInfo(name = "extended_ingredients") val extendedIngredients: @RawValue List<ExtendedIngredient>?,
)