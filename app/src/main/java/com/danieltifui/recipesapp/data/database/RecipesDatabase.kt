package com.danieltifui.recipesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danieltifui.recipesapp.data.database.favoritedb.FavoritesDao
import com.danieltifui.recipesapp.data.database.favoritedb.IngredientsListTypeConverter
import com.danieltifui.recipesapp.data.database.favoritedb.InstructionsTypeConverter
import com.danieltifui.recipesapp.data.database.grocery.GroceryDao
import com.danieltifui.recipesapp.data.database.grocery.GroceryIngredientTypeConverter
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.models.Result

@Database(
    entities = [RecipesEntity::class, Result::class, GroceryRecipesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    RecipesTypeConverter::class,
    InstructionsTypeConverter::class,
    IngredientsListTypeConverter::class,
    GroceryIngredientTypeConverter::class
)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDAO

    abstract fun favoritesDao(): FavoritesDao

    abstract fun groceryDao(): GroceryDao
}