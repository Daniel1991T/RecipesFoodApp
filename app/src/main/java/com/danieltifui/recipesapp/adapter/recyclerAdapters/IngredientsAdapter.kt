package com.danieltifui.recipesapp.adapter.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.databinding.IngredientsRowLayoutBinding
import com.danieltifui.recipesapp.models.ExtendedIngredient
import com.danieltifui.recipesapp.untils.Constants.Companion.BASE_IMAGE_URL
import com.danieltifui.recipesapp.untils.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount() = ingredientsList.size

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: IngredientsRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredients: ExtendedIngredient) {
            binding.ingredientsImageView.load(BASE_IMAGE_URL + ingredients.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            binding.ingredientsName.text = ingredients.name.capitalize(Locale.ROOT)
            binding.ingredientsAmount.text = ingredients.amount.toString()
            binding.ingredientsUnit.text = ingredients.unit
            binding.ingredientsConsistency.text = ingredients.consistency
            binding.ingredientsOriginal.text = ingredients.original
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}