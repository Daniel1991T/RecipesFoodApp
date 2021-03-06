package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.repository.DataStoreRepository
import com.danieltifui.recipesapp.untils.Constants.Companion.API_KEY
import com.danieltifui.recipesapp.untils.Constants.Companion.API_KEY2
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_DIET_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_QUERY_NUMBER
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_APY_KEY
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_DIET
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_NUMBER
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_SEARCH
import com.danieltifui.recipesapp.untils.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType
                .collect { value ->
                    mealType = value.selectedMealType
                    dietType = value.selectedDietType
                }
        }

        queries[QUERY_NUMBER] = DEFAULT_QUERY_NUMBER
        queries[QUERY_APY_KEY] = API_KEY2
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun searchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_QUERY_NUMBER
        queries[QUERY_APY_KEY] = API_KEY2
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}