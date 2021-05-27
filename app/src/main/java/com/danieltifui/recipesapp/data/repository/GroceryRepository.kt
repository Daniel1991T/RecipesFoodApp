package com.danieltifui.recipesapp.data.repository

import com.danieltifui.recipesapp.data.LocalDataSource
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.data.database.grocery.Ingredients
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.untils.safeCall
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GroceryRepository @Inject constructor(
    groceryDatabase: LocalDataSource
) {
    private val database = groceryDatabase

    suspend fun insertGrocery(grocery: GroceryRecipesEntity): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.insertGrocery(grocery)
                Resource.Success(result)
            }
        }
    }

    suspend fun updateGroceryRecipes(grocery: GroceryRecipesEntity): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.updateGroceryRecipe(grocery)
                Resource.Success(result)
            }
        }
    }

    suspend fun getAllGrocery(): Resource<List<GroceryRecipesEntity>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.getAllGrocery()
                Resource.Success(result)
            }
        }
    }

    suspend fun getSpecificGrocery(id: Int): Resource<GroceryRecipesEntity> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = database.getSpecificGrocery(id)
                Resource.Success(result)
            }
        }
    }

}