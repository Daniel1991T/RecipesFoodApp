package com.danieltifui.recipesapp.data.database.grocery

import androidx.room.*

@Dao
interface GroceryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(groceryRecipesEntity: GroceryRecipesEntity)

    @Query("SELECT * FROM grocery_recipe_table ORDER BY name ASC")
    suspend fun getAllGrocery(): List<GroceryRecipesEntity>

    @Query("SELECT * FROM grocery_recipe_table WHERE id == :id")
    suspend fun getSpecificGrocery(id: Int): GroceryRecipesEntity

    @Update
    suspend fun updateGrocery(groceryRecipesEntity: GroceryRecipesEntity)

}