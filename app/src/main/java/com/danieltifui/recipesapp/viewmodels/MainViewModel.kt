package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.danieltifui.recipesapp.data.database.RecipesEntity
import com.danieltifui.recipesapp.data.repository.DefaultDataSourceRepository
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.untils.Constants.Companion.LIMITED_API_KEY_CODE
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT
import com.danieltifui.recipesapp.untils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val dataSourceRepository: DefaultDataSourceRepository
): AndroidViewModel(application) {

    /** ROOM DATABASE **/
    val readRecipes: LiveData<List<RecipesEntity>> = dataSourceRepository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope
        .launch(Dispatchers.IO) {
            dataSourceRepository.local.insertRecipes(recipesEntity)
        }


    /** RETROFIT **/
    private var _recipesResponse: MutableLiveData<Resource<FoodRecipe>> = MutableLiveData()
    var recipesResponse: LiveData<Resource<FoodRecipe>> = _recipesResponse

    private var _searchedRecipesResponse: MutableLiveData<Resource<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: LiveData<Resource<FoodRecipe>> = _searchedRecipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        Log.d("RecipesFragment", "searchRecipes: $searchQuery")
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                val response = dataSourceRepository.remote.getRecipes(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)
                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) {
                    Log.d(TAG_FRAGMENT, "getRecipesSafeCall: $foodRecipe")
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                _recipesResponse.value = Resource.Error("Recipes not found.")
            }
        } else {
            _recipesResponse.value = Resource.Error("No Internet Conection")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _searchedRecipesResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                val response = dataSourceRepository.remote.searchRecipes(searchQuery)
                Log.d(TAG_FRAGMENT, "searchRecipesSafeCall: ${response.body()}")
                _searchedRecipesResponse.value = handleFoodRecipesResponse(response)
                Log.d(TAG_FRAGMENT, "searchRecipesSafeCall: ${searchedRecipesResponse.value}")
            } catch (e: Exception) {
                _searchedRecipesResponse.value = Resource.Error("Recipes not found.")
            }
        } else {
            _searchedRecipesResponse.value = Resource.Error("No Internet Conection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): Resource<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }
            response.code() == LIMITED_API_KEY_CODE -> {
                return Resource.Error("API Key Limit")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return Resource.Error("Recipes Not found.")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return Resource.Success(foodRecipe!!)
            }
            else -> {
                return Resource.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}