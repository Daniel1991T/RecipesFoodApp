package com.danieltifui.recipesapp.data

import com.danieltifui.recipesapp.data.database.RecipesDAO
import com.danieltifui.recipesapp.data.database.RecipesEntity
import com.danieltifui.recipesapp.data.database.favoritedb.FavoritesDao
import com.danieltifui.recipesapp.data.database.grocery.GroceryDao
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO,
    private val favoritesDao: FavoritesDao,
    private val groceryDao: GroceryDao
) {

    suspend fun insertGrocery(groceryRecipesEntity: GroceryRecipesEntity) {
        groceryDao.insertRecipes(groceryRecipesEntity)
    }

    suspend fun getAllGrocery(): List<GroceryRecipesEntity> {
        return groceryDao.getAllGrocery()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDAO.readRecipes()
    }

    fun getAllFavoriteRecipes(): List<Result> {
        return favoritesDao.getAllFavoritesRecipes()
    }


    suspend fun insertFavoriteRecipes(favoritesRecipeEntity: Result) {
        favoritesDao.insertRecipes(favoritesRecipeEntity)
    }

    suspend fun getIsFavoriteRecipes(recipesId: Int): Int {
        return favoritesDao.getFavoriteRecipes(recipesId)
    }

    suspend fun deleteFromFavoriteRecipes(recipes: Result) {
        favoritesDao.delete(recipes)
    }

    suspend fun deleteAllFavoriteRecipes() {
        favoritesDao.deleteAll()
    }

}