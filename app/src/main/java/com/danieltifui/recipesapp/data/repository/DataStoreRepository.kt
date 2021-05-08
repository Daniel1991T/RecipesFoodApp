package com.danieltifui.recipesapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_DIET_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.PREFERENCE_DIET_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.danieltifui.recipesapp.untils.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import com.danieltifui.recipesapp.untils.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKey {
        val selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore
    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.selectedMealType] = mealType
            preferences[PreferenceKey.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKey.selectedDietType] = dietType
            preferences[PreferenceKey.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw  exception
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKey.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKey.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKey.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKey.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)