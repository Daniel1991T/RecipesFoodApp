package com.danieltifui.recipesapp.ui.fragmets.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.recyclerAdapters.RecipesAdapter
import com.danieltifui.recipesapp.databinding.FragmentFavoriteRecipesBinding
import com.danieltifui.recipesapp.databinding.FragmentRecipesBinding
import com.danieltifui.recipesapp.models.FoodRecipe
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.viewmodels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoriteViewModel by viewModels()

    private val mAdapter by lazy { RecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        subscribeToObservers()
        favoritesViewModel.getFavoriteRecipes()
        return binding.root
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