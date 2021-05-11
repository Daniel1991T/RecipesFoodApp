package com.danieltifui.recipesapp.ui.fragmets.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.recyclerAdapters.IngredientsAdapter
import com.danieltifui.recipesapp.databinding.FragmentIngredientsBinding
import com.danieltifui.recipesapp.databinding.FragmentInstructionsBinding
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Constants
import com.danieltifui.recipesapp.untils.Constants.Companion.DETAILS_BUNDLE_KEY

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val myAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(DETAILS_BUNDLE_KEY)
        setupRecyclerView()
        myBundle?.extendedIngredients?.let { myAdapter.setData(it) }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.ingredientsRecyclerView.adapter = myAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}