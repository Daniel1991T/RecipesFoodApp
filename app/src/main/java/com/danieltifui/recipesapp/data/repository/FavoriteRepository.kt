package com.danieltifui.recipesapp.data.repository

import com.danieltifui.recipesapp.data.LocalDataSource
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.untils.safeCall
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class FavoriteRepository @Inject constructor(
    favoriteRepository: LocalDataSource
){
    private val database = favoriteRepository

    suspend fun insertFavoriteRecipes(recipes: Result): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.insertFavoriteRecipes(recipes)
                Resource.Success(Any())
            }
        }
    }
}