package com.danieltifui.recipesapp.ui.fragmets.grocery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.adapter.recyclerAdapters.GroceryListAdapter
import com.danieltifui.recipesapp.databinding.FragmentGroceryBinding
import com.danieltifui.recipesapp.untils.Resource
import com.danieltifui.recipesapp.viewmodels.GroceryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryFragment : Fragment() {

    private var _binding: FragmentGroceryBinding? = null
    private val binding get() = _binding!!
    private val groceryViewModel: GroceryViewModel by viewModels()
    private val mAdapter by lazy { GroceryListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGroceryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        groceryViewModel.getAllGroceryList()
        subscribeToObservers()


        return binding.root
    }

    private fun subscribeToObservers() {
        groceryViewModel.groceryList.observe(viewLifecycleOwner, { response ->
            Log.d("groceryFragment", "subscribeToObservers: $response")
            when(response) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    Log.d("groceryFragment", "subscribeToObservers: ${response.data}")
                    response.data?.let { mAdapter.setData(it) }
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
        binding.groceryRecyclerView.adapter = mAdapter
        binding.groceryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.groceryRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.groceryRecyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}