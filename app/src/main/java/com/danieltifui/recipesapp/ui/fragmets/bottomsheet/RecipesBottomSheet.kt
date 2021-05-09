package com.danieltifui.recipesapp.ui.fragmets.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.danieltifui.recipesapp.databinding.FragmentRecipesBottomSheetBinding
import com.danieltifui.recipesapp.ui.fragmets.recipes.viewmodels.RecipesViewModel
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_DIET_TYPE
import com.danieltifui.recipesapp.untils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.danieltifui.recipesapp.untils.observeOnce
import com.danieltifui.recipesapp.untils.scrollToPosition
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.*

const val TAG = "RecipesBottomSheet"

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var _binding: FragmentRecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBottomSheetBinding.inflate(inflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observeOnce(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.chipGroupTypeMeal)
            Log.d(TAG, "onCreateView: ${value.selectedMealTypeId.toString()}")
            binding.horizontalScrollViewMealType.scrollToPosition<Chip>(value.selectedMealTypeId)
            updateChip(value.selectedDietTypeId, binding.groupChipDietType)
            Log.d(TAG, "onCreateView: ${value.selectedDietTypeId.toString()}")
            binding.horizontalScrollViewDiet.scrollToPosition<Chip>(value.selectedDietTypeId)

        })

        binding.chipGroupTypeMeal.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            mealTypeChip = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChipId = checkedId

        }
        binding.groupChipDietType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            dietTypeChip = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChipId = checkedId
        }
        binding.bottomApply.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip, mealTypeChipId, dietTypeChip, dietTypeChipId
            )
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun updateChip(selectedChipId: Int, groupChip: ChipGroup) {
        if (selectedChipId != 0) {
            try {
                groupChip.findViewById<Chip>(selectedChipId).isChecked = true
            } catch (e: Exception) {
                Log.d(TAG, "updateChip: ${e.message.toString()}")
            }
        }
    }

}