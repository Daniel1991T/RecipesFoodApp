package com.danieltifui.recipesapp.adapter.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danieltifui.recipesapp.databinding.InstructionRowLayoutBinding
import com.danieltifui.recipesapp.models.Step
import com.danieltifui.recipesapp.untils.RecipesDiffUtil
import java.util.*

class InstructionAdapter : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {

    private var instructionStepsList = emptyList<Step>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instructionStepsList[position])
    }

    override fun getItemCount() = instructionStepsList.size

    fun setData(newStepList: List<Step>) {
        val ingredientsDiffUtil = RecipesDiffUtil(instructionStepsList, newStepList)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        instructionStepsList = newStepList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: InstructionRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(instruction: Step) {
            binding.numberTextView.text = instruction.number.toString()
            binding.stepTextView.text = instruction.step
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InstructionRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}