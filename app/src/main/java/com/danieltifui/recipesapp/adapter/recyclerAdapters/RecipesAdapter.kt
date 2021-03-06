package com.danieltifui.recipesapp.adapter.recyclerAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danieltifui.recipesapp.databinding.RecipesRowLayoutBinding
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT
import com.danieltifui.recipesapp.untils.RecipesDiffUtil

class RecipesAdapter(private var layoutTag: String) : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder.from(parent, tag = layoutTag)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.binding(currentRecipe)
    }

    override fun getItemCount() = recipes.size

    fun setData(newRecipes: FoodRecipe) {
        val recipeDiffUtil = RecipesDiffUtil(recipes, newRecipes.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        recipes = newRecipes.results
        diffUtilResult.dispatchUpdatesTo(this)
    }


    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup, tag: String): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                binding.recipesRowLayout.tag = tag
                return MyViewHolder(binding)
            }
        }
    }
}