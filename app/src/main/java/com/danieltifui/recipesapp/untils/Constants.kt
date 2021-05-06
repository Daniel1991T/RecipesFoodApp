package com.danieltifui.recipesapp.untils

class Constants {
    companion object {
        const val API_KEY = "068b652a558b46c6b0768788b046e882"
        const val BASE_URL = "https://api.spoonacular.com"
        const val LIMITED_API_KEY_CODE = 402

        const val QUERY_NUMBER = "number"
        const val QUERY_APY_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // TAGS for logs
        const val TAG_FRAGMENT = "RecipesFragment"
    }
}