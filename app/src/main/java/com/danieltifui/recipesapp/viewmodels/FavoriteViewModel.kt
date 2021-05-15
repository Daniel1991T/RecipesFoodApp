package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.repository.FavoriteRepository
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    application: Application
) : AndroidViewModel(application){

    private val _favoriteRecipes = MutableLiveData<Resource<List<Result>>>()
    val favoriteRecipes: LiveData<Resource<List<Result>>> = _favoriteRecipes

    fun getFavoriteRecipes() {
        _favoriteRecipes.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteRepository.getFavoriteRecipes()
            _favoriteRecipes.postValue(result)
        }
    }
}