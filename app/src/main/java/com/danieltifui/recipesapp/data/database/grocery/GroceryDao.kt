package com.danieltifui.recipesapp.data.database.grocery

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroceryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(groceryRecipesEntity: GroceryRecipesEntity)

    @Query("SELECT * FROM grocery_recipe_table ORDER BY name ASC")
    suspend fun getAllGrocery(): List<GroceryRecipesEntity>
}