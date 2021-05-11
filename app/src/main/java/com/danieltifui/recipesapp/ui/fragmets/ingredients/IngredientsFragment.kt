package com.danieltifui.recipesapp.ui.fragmets.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.databinding.FragmentIngredientsBinding
import com.danieltifui.recipesapp.databinding.FragmentInstructionsBinding

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentIngredientsBinding.inflate(inflater, container, false)


        return binding.root
    }

}