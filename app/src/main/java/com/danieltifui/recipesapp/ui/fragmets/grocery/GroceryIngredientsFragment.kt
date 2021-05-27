package com.danieltifui.recipesapp.ui.fragmets.grocery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.recyclerAdapters.GroceryIngredientsAdapter
import com.danieltifui.recipesapp.databinding.FragmentGroceryIngredientsBinding
import com.danieltifui.recipesapp.databinding.IngedientsGroceryRowLayoutBinding
import com.danieltifui.recipesapp.databinding.IngredientsRowLayoutBinding
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.viewmodels.GroceryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryIngredientsFragment : Fragment() {

    private var _binding: FragmentGroceryIngredientsBinding? = null
    private val binding: FragmentGroceryIngredientsBinding get() = _binding!!
    private val mAdapter: GroceryIngredientsAdapter by lazy { GroceryIngredientsAdapter() }
    private val groceryViewModel: GroceryViewModel by viewModels()
    val args: GroceryIngredientsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentGroceryIngredientsBinding.inflate(inflater, container, false)
        setupRecyclerView()

        groceryViewModel.getSpecificGrocery(args.id)
        subscribeToObserver()

        mAdapter.setOnCheckedClickListener { ingredients ->
            Log.d("TAG", "onCreateView: $ingredients")
            val result = groceryViewModel.ingredientsList.value?.data
            result?.ingredients?.map { item ->
                if (item.name == ingredients.name) {
                    item.apply {
                        item.isBought = !item.isBought!!
                    }
                }
            }
            result?.let { groceryViewModel.updateGroceryRecipes(it) }
        }


        return binding.root
    }

    private fun subscribeToObserver() {
        groceryViewModel.ingredientsList.observe(viewLifecycleOwner, { response ->
            Log.d("TAG", "subscribeToObserver: ${response.data}")
            when(response) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        Log.d("TAG", "subscribeToObserver: ${it.ingredients}")
                        mAdapter.setData(it.ingredients)
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        showShimmerEffect()
        binding.groceryIngredientsRecyclerView.adapter = mAdapter
        binding.groceryIngredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showShimmerEffect() {
        binding.groceryIngredientsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.groceryIngredientsRecyclerView.hideShimmer()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}