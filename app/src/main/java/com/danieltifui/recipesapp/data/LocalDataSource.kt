package com.danieltifui.recipesapp.data

import com.danieltifui.recipesapp.data.database.RecipesDAO
import com.danieltifui.recipesapp.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }
}