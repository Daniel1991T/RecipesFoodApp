package com.danieltifui.recipesapp.ui.fragmets.instructions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.recyclerAdapters.InstructionAdapter
import com.danieltifui.recipesapp.databinding.FragmentInstructionsBinding
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.models.Step
import com.danieltifui.recipesapp.untils.Constants
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT


class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: InstructionAdapter by lazy { InstructionAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.DETAILS_BUNDLE_KEY)
        setupRecyclerView()
        myBundle?.analyzedInstructions?.get(0)?.steps.let {
            if (it != null) {
                binding.errorImageView.visibility = View.GONE
                binding.errorTextView.visibility = View.GONE
                mAdapter.setData(it)
            } else {
                binding.errorImageView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.VISIBLE
            }
        }


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.instructionRecyclerView.adapter = mAdapter
        binding.instructionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}