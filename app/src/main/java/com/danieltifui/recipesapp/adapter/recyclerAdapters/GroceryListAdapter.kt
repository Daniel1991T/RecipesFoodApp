package com.danieltifui.recipesapp.adapter.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danieltifui.recipesapp.data.database.grocery.GroceryRecipesEntity
import com.danieltifui.recipesapp.databinding.GroceryRowLayoutBinding
import com.danieltifui.recipesapp.untils.RecipesDiffUtil

class GroceryListAdapter : RecyclerView.Adapter<GroceryListAdapter.ViewHolder>() {

    private var groceryList = emptyList<GroceryRecipesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(groceryList[position])
    }

    override fun getItemCount() = groceryList.size

    fun setData(newGroceryList: List<GroceryRecipesEntity>) {
        val groceryListDiffUtil = RecipesDiffUtil(groceryList, newGroceryList)
        val diffUtilResult = DiffUtil.calculateDiff(groceryListDiffUtil)
        groceryList = newGroceryList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: GroceryRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(grocery: GroceryRecipesEntity) {
            binding.groceryRecipesEntity = grocery
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroceryRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}