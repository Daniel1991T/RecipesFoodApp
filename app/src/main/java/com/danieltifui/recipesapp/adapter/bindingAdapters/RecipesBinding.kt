package com.danieltifui.recipesapp.adapter.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.danieltifui.recipesapp.data.database.RecipesEntity
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.untils.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponseText", "readDatabaseText", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else {
                textView.visibility = View.INVISIBLE
            }
        }


    }
}