package com.danieltifui.recipesapp.ui.fragmets.recipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.danieltifui.recipesapp.untils.Constants
import com.danieltifui.recipesapp.untils.Constants.Companion.API_KEY
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_APY_KEY
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_DIET
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_NUMBER
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_TYPE

class RecipesViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_APY_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}