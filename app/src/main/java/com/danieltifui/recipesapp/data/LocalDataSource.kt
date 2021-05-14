package com.danieltifui.recipesapp.data

import com.danieltifui.recipesapp.data.database.RecipesDAO
import com.danieltifui.recipesapp.data.database.RecipesEntity
import com.danieltifui.recipesapp.data.database.favoritedb.FavoritesDao
import com.danieltifui.recipesapp.data.database.favoritedb.FavoritesRecipeEntity
import com.danieltifui.recipesapp.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO,
    private val favoritesDao: FavoritesDao
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    suspend fun getAllFavoriteRecipes(): Flow<List<Result>> {
        return favoritesDao.getAllFavoritesRecipes()
    }

    suspend fun insertFavoriteRecipes(favoritesRecipeEntity: Result) {
        favoritesDao.insertRecipes(favoritesRecipeEntity)
    }
}