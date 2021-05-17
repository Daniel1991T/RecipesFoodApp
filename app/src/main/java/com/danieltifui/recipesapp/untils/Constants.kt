package com.danieltifui.recipesapp.untils

class Constants {
    companion object {
        const val API_KEY = "068b652a558b46c6b0768788b046e882"
        const val API_KEY2 = "9d7580a4dcd5466fa6aa781807dd9828"
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val LIMITED_API_KEY_CODE = 402

        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_APY_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITES_RECIPE_TABLE = "favorite_recipe_table"
        const val GROCERY_RECIPE_TABLE = "grocery_recipe_table"

        // TAGS for logs
        const val TAG_FRAGMENT = "RecipesFragment"
        const val TAG_DETAILS_ACTIVITY = "DetailsActivity"

        //Bottom Sheet and Preferences
        const val PREFERENCE_NAME = "food_preferences"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "vegan"
        const val DEFAULT_QUERY_NUMBER = "50"
        const val PREFERENCE_MEAL_TYPE = "mealType"
        const val PREFERENCE_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCE_DIET_TYPE = "dietType"
        const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"

        const val PREFERENCE_BACK_ONLINE = "backOnline"

        // key args
        const val DETAILS_BUNDLE_KEY = "recipeBundle"

    }
}