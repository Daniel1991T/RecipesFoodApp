package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.database.favoritedb.FavoritesRecipeEntity
import com.danieltifui.recipesapp.data.repository.DefaultDataSourceRepository
import com.danieltifui.recipesapp.data.repository.FavoriteRepository
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

    private var _insertFavoriteRecipesStatus = MutableLiveData<Resource<Any>>()
    var insertFavoriteRecipesStatus: MutableLiveData<Resource<Any>> = _insertFavoriteRecipesStatus

    fun insertFavoriteRecipes(recipes: Result) {
        _insertFavoriteRecipesStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteRepository.insertFavoriteRecipes(recipes)
            _insertFavoriteRecipesStatus.postValue(Resource.Success(result))
        }
    }
}