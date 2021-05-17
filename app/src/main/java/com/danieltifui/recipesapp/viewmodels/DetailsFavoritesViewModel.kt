package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.data.database.grocery.Ingredients
import com.danieltifui.recipesapp.data.repository.FavoriteRepository
import com.danieltifui.recipesapp.data.repository.GroceryRepository
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _isFavoriteStatus = MutableLiveData<Resource<Int>>()
    var isFavoriteStatus: LiveData<Resource<Int>> = _isFavoriteStatus

    private var _insertFavoriteRecipesStatus = MutableLiveData<Resource<Any>>()
    var insertFavoriteRecipesStatus: LiveData<Resource<Any>> = _insertFavoriteRecipesStatus

    private var _deleteFavoriteRecipesStatus = MutableLiveData<Resource<Any>>()
    var deleteFavoriteRecipesStatus: LiveData<Resource<Any>> = _deleteFavoriteRecipesStatus


    fun getIsFavoriteStatus(recipesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteRepository.getFavoriteStatus(recipesId)
            Log.d("test", "getIsFavoriteStatus: ${result.data}")
            _isFavoriteStatus.postValue(result)
        }
    }

    fun insertFavoriteRecipes(recipes: Result) {
        _insertFavoriteRecipesStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteRepository.insertFavoriteRecipes(recipes)
            _insertFavoriteRecipesStatus.postValue(Resource.Success(result))
        }
    }

    fun deleteFromFavoriteRecipes(recipes: Result) {
        _deleteFavoriteRecipesStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteRepository.deleteFromFavoriteRecipes(recipes)
            _deleteFavoriteRecipesStatus.postValue(Resource.Success(result))
        }
    }
}