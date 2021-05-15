package com.danieltifui.recipesapp.data.repository

import android.util.Log
import com.danieltifui.recipesapp.data.LocalDataSource
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_DETAILS_ACTIVITY
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.untils.safeCall
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception
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

    suspend fun deleteFromFavoriteRecipes(recipes: Result): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.deleteFromFavoriteRecipes(recipes)
                Resource.Success(Any())
            }
        }
    }

    suspend fun getFavoriteStatus(recipesId: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.getIsFavoriteRecipes(recipesId)
                Log.d("test", "getFavoriteStatus repository: $result")
                Resource.Success(result)
            }
        }
    }
}