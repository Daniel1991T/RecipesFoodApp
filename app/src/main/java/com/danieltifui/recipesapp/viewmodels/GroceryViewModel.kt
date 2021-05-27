package com.danieltifui.recipesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.data.database.grocery.Ingredients
import com.danieltifui.recipesapp.data.repository.GroceryRepository
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    application: Application,
    private val groceryRepository: GroceryRepository
) : AndroidViewModel(application){

    private var _insertGroceryStatus = MutableLiveData<Resource<Any>>()
    val insertGroceryStatus: LiveData<Resource<Any>> = _insertGroceryStatus

    private var _groceryList = MutableLiveData<Resource<List<GroceryRecipesEntity>>>()
    val groceryList: LiveData<Resource<List<GroceryRecipesEntity>>> = _groceryList

    private var _ingredientsList = MutableLiveData<Resource<GroceryRecipesEntity>>()
    val ingredientsList: LiveData<Resource<GroceryRecipesEntity>> = _ingredientsList

    fun updateGroceryRecipes(entity: GroceryRecipesEntity) {
        viewModelScope.launch {
            groceryRepository.updateGroceryRecipes(entity)
        }
    }

    fun getSpecificGrocery(id: Int) {
        _ingredientsList.postValue(Resource.Loading())
        viewModelScope.launch {
            val result = groceryRepository.getSpecificGrocery(id)
            _ingredientsList.postValue(result)
        }
    }

    fun getAllGroceryList() {
        _groceryList.postValue(Resource.Loading())
        viewModelScope.launch {
            val result = groceryRepository.getAllGrocery()
            _groceryList.postValue(result)
        }
    }

    fun insertGroceryRecipes(recipes: Result) {
        viewModelScope.launch {
            val listOfIngredient = mutableListOf<Ingredients>()
            recipes.extendedIngredients?.forEach { it ->
                listOfIngredient.add(
                    Ingredients(
                        amount = it.amount,
                        name = it.name,
                        original = it.original,
                        unit = it.unit,
                        consistency = it.consistency,
                        image = it.image
                    )
                )
            }
            val name = recipes.title
            val result = groceryRepository.insertGrocery(
                GroceryRecipesEntity(
                    id = recipes.id,
                    name = name,
                    ingredients = listOfIngredient
                )
            )
            _insertGroceryStatus.postValue(Resource.Success(result))
        }
    }
}