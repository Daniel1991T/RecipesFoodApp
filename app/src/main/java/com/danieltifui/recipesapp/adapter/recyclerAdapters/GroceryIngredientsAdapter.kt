package com.danieltifui.recipesapp.adapter.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.data.database.grocery.Ingredients
import com.danieltifui.recipesapp.databinding.IngedientsGroceryRowLayoutBinding
import com.danieltifui.recipesapp.untils.RecipesDiffUtil

class GroceryIngredientsAdapter : RecyclerView.Adapter<GroceryIngredientsAdapter.ViewHolder>() {

    private var grocerIngredients = emptyList<Ingredients>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(grocerIngredients[position])
        holder.itemView.setOnClickListener {
            onCheckedClickListener?.let { click ->
                click(grocerIngredients[position])
            }
        }
    }

    override fun getItemCount() = grocerIngredients.size

    fun setData(newIngredients: List<Ingredients>) {
        val ingredientsDiffUtil = RecipesDiffUtil(grocerIngredients, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        grocerIngredients = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private var onCheckedClickListener: ((Ingredients) -> Unit)? = null

    fun setOnCheckedClickListener(listener: (Ingredients) -> Unit) {
        onCheckedClickListener = listener
    }

    class ViewHolder(private val binding: IngedientsGroceryRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: Ingredients) {
            val name = if (ingredients.name.isNotEmpty()) ingredients.name else ingredients.original
            val text = "$name  ${ingredients.amount}  ${ingredients.unit}"
            binding.ingredientCheckBox.text = text
            binding.ingredientCheckBox.isChecked = ingredients.isBought == true

        }

        companion object {
            fun from(parent: ViewGroup): GroceryIngredientsAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngedientsGroceryRowLayoutBinding.inflate(layoutInflater, parent, false)
                return GroceryIngredientsAdapter.ViewHolder(binding)
            }
        }
    }
}