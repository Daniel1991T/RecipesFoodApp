package com.danieltifui.recipesapp.data

import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.data.network.FoodRecipesApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class RemoteDataSource @Inject constructor(
    private var foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}