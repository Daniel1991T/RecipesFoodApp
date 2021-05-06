package com.danieltifui.recipesapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.repository.DefaultDataSourceRepository
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.untils.Constants.Companion.LIMITED_API_KEY_CODE
import com.danieltifui.recipesapp.untils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val applicationContext: Context,
    private val dataSourceRepository: DefaultDataSourceRepository
): AndroidViewModel(application) {

    private var _recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var recipesResponse: LiveData<NetworkResult<FoodRecipe>> = _recipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = dataSourceRepository.getRemoteDataSource().getRecipes(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                _recipesResponse.value = NetworkResult.Error(applicationContext.getString(R.string.error_recipes_not_found))
            }
        } else {
            _recipesResponse.value = NetworkResult.Error(applicationContext.getString(R.string.error_internet_connection))
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(applicationContext.getString(R.string.error_timeout))
            }
            response.code() == LIMITED_API_KEY_CODE -> {
                return NetworkResult.Error(applicationContext.getString(R.string.error_api_key_limit))
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(applicationContext.getString(R.string.error_recipes_not_found))
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
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