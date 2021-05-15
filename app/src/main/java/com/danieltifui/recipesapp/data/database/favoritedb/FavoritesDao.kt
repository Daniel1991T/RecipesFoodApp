package com.danieltifui.recipesapp.data.database.favoritedb

import androidx.room.*
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(favoritesRecipeEntity: Result)

    @Query("SELECT * FROM favorite_recipe_table ORDER BY source_name ASC")
    fun getAllFavoritesRecipes(): Flow<List<Result>>

    @Query("SELECT id FROM favorite_recipe_table WHERE id = :id")
    suspend fun getFavoriteRecipes(id: Int): Int

    @Delete
    suspend fun delete(favoritesRecipeEntity: Result)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAll()
}