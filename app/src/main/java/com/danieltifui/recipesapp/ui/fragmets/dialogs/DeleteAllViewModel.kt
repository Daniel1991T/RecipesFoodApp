package com.danieltifui.recipesapp.ui.fragmets.dialogs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject constructor(
    application: Application,
    private val favoriteRepository: FavoriteRepository
) : AndroidViewModel(application){

    fun deleteAllFavoriteRecipes() = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.deleteAllFavoriteRecipes()
    }
}