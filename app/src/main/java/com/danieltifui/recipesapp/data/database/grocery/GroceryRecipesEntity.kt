package com.danieltifui.recipesapp.data.database.grocery

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danieltifui.recipesapp.untils.Constants.Companion.GROCERY_RECIPE_TABLE
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = GROCERY_RECIPE_TABLE)
data class GroceryRecipesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val ingredients: @RawValue List<Ingredients>
) : Parcelable

@Parcelize
data class Ingredients(
    @SerializedName("isBought")
    var isBought: Boolean? = false,
    @SerializedName("amount")
    val amount: Double? = 0.0,
    @SerializedName("consistency")
    val consistency: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("original")
    val original: String = "",
    @SerializedName("unit")
    val unit: String? = ""
) : Parcelable