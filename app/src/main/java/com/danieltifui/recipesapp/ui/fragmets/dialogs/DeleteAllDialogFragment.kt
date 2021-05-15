package com.danieltifui.recipesapp.ui.fragmets.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.danieltifui.recipesapp.databinding.FragmentDeleteAllDialogBinding
import com.danieltifui.recipesapp.untils.snackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDeleteAllDialogBinding? = null
    private val binding get() = _binding!!
    private val deleteAllViewModel: DeleteAllViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteAllDialogBinding.inflate(inflater, container, false)

        binding.deleteAllRecipesButton.setOnClickListener {
            deleteAllViewModel.deleteAllFavoriteRecipes()
            snackbar("All Favorite Recipes were deleted")
            findNavController().navigate(DeleteAllDialogFragmentDirections.actionDeleteAllDialogFragmentToRecipesFragment())
        }

        binding.noDeleteRecipesButton.setOnClickListener {
            if (findNavController().previousBackStackEntry != null) {
                findNavController().popBackStack()
            } else {
                findNavController().navigate(DeleteAllDialogFragmentDirections.actionDeleteAllDialogFragmentToFavoriteRecipesFragment())
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}