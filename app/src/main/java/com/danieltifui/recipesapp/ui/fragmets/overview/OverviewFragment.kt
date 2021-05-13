package com.danieltifui.recipesapp.ui.fragmets.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.databinding.FragmentOverviewBinding
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.untils.Constants.Companion.DETAILS_BUNDLE_KEY
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_DETAILS_ACTIVITY
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(DETAILS_BUNDLE_KEY)

        binding.mainImageView.load(myBundle?.image)
        binding.titleDetailsTextView.text = myBundle?.title
        binding.likesTextView.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()
        Log.d(TAG_DETAILS_ACTIVITY, "onCreateView: $myBundle")
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.summaryTextView.text = summary
        }
        setMarker(
            myBundle?.vegetarian == true,
            binding.vegetarianTextView,
            binding.vegetarianImageView
        )
        setMarker(
            myBundle?.vegan == true,
            binding.veganTextView,
            binding.veganImageView
        )
        setMarker(
            myBundle?.dairyFree == true,
            binding.dairyFreeTextView,
            binding.dairyFreeImageView
        )
        setMarker(
            myBundle?.glutenFree == true,
            binding.glutenFreeTextView,
            binding.glutenFreeImageView
        )
        setMarker(
            myBundle?.veryHealthy == true,
            binding.healthyTextView,
            binding.healthyImageView
        )
        setMarker(
            myBundle?.cheap == true,
            binding.cheapTextView,
            binding.cheapImageView
        )
        return binding.root
    }

    private fun setMarker(isMarked: Boolean, text: TextView, image: ImageView) {
        if (isMarked) {
            text.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

}