package com.danieltifui.recipesapp.models

import android.os.Parcelable
import androidx.room.*
import com.danieltifui.recipesapp.data.database.favoritedb.InstructionsTypeConverter
import com.danieltifui.recipesapp.untils.Constants.Companion.FAVORITES_RECIPE_TABLE
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = FAVORITES_RECIPE_TABLE)
data class Result(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("aggregateLikes")
    @ColumnInfo(name = "aggregate_likes")
    val aggregateLikes: Int?,
    @SerializedName("cheap")
    val cheap: Boolean?,
    @SerializedName("dairyFree")
    @ColumnInfo(name = "dairy_free")
    val dairyFree: Boolean,
    @SerializedName("extendedIngredients")
    @ColumnInfo(name = "extended_ingredients")
    val extendedIngredients: @RawValue List<ExtendedIngredient>? = emptyList(),
    @SerializedName("glutenFree")
    @ColumnInfo(name = "gluten_free")
    val glutenFree: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    @ColumnInfo(name = "ready_in_minutes")
    val readyInMinutes: Int,
    @SerializedName("sourceName")
    @ColumnInfo(name = "source_name")
    val sourceName: String? = "",
    @SerializedName("sourceUrl")
    @ColumnInfo(name = "source_url")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    @SerializedName("analyzedInstructions")
    @ColumnInfo(name = "analyzed_instruction")
    val analyzedInstructions: @RawValue List<AnalyzedInstructions>? = emptyList()
) : Parcelable