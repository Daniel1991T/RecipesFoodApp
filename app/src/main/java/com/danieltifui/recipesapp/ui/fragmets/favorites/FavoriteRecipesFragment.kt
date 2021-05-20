package com.danieltifui.recipesapp.ui.fragmets.favorites

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.recyclerAdapters.RecipesAdapter
import com.danieltifui.recipesapp.databinding.FragmentFavoriteRecipesBinding
import com.danieltifui.recipesapp.databinding.FragmentRecipesBinding
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.untils.Constants.Companion.FAVORITE_LAYOUT_TAG
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.untils.snackbar
import com.danieltifui.recipesapp.viewmodels.FavoriteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoriteViewModel by viewModels()

    private val mAdapter by lazy { RecipesAdapter(FAVORITE_LAYOUT_TAG) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        subscribeToObservers()
        favoritesViewModel.getFavoriteRecipes()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_contextual_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_favorite_recipe_menu) {
            findNavController().navigate(R.id.action_favoriteRecipesFragment_to_deleteAllDialogFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeToObservers() {
        favoritesViewModel.favoriteRecipes.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideShimmerEffect()
                        val result = response.data?.let { FoodRecipe(it) }
                        if (result != null) {
                            mAdapter.setData(result)
                            showError(false)
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    showError(true)
                }
                is Resource.Loading -> {
                    showError(false)
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.favoritesRecyclerView.adapter = mAdapter
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.favoritesRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.favoritesRecyclerView.hideShimmer()
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.errorImageView.visibility = View.VISIBLE
            binding.errorTextView.visibility = View.VISIBLE
        } else {
            binding.errorImageView.visibility = View.INVISIBLE
            binding.errorTextView.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}