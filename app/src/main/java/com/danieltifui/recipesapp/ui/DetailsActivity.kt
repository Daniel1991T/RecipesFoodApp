package com.danieltifui.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.pager.PagerAdapter
import com.danieltifui.recipesapp.databinding.ActivityDetailsBinding
import com.danieltifui.recipesapp.ui.fragmets.ingredients.IngredientsFragment
import com.danieltifui.recipesapp.ui.fragmets.instructions.InstructionsFragment
import com.danieltifui.recipesapp.ui.fragmets.overview.OverviewFragment
import com.danieltifui.recipesapp.untils.Constants.Companion.DETAILS_BUNDLE_KEY
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailsToolbar)
        binding.detailsToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = arrayListOf<Fragment>(
            OverviewFragment(),
            IngredientsFragment(),
            InstructionsFragment()
        )
        val titles = arrayListOf<String>(
            "Overview",
            "Ingredients",
            "Instructions"
        )

        val resultBundle = Bundle()
        resultBundle.putParcelable(DETAILS_BUNDLE_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.detailsViewPager.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(binding.detailsTabLayout, binding.detailsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}