package com.danieltifui.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgs
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.adapter.pager.PagerAdapter
import com.danieltifui.recipesapp.databinding.ActivityDetailsBinding
import com.danieltifui.recipesapp.ui.fragmets.ingredients.IngredientsFragment
import com.danieltifui.recipesapp.ui.fragmets.instructions.InstructionsFragment
import com.danieltifui.recipesapp.ui.fragmets.overview.OverviewFragment
import com.danieltifui.recipesapp.untils.Constants.Companion.DETAILS_BUNDLE_KEY
import com.danieltifui.recipesapp.untils.snackbar
import com.danieltifui.recipesapp.viewmodels.DetailsFavoritesViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var binding: ActivityDetailsBinding
    private val detailsFavoritesViewModel: DetailsFavoritesViewModel by viewModels<DetailsFavoritesViewModel>()
    private lateinit var menuItem: MenuItem

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_save_favorite_menu, menu)
        menuItem = menu!!.findItem(R.id.save_favorite_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_favorite_menu) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favorites = args.result
        detailsFavoritesViewModel.insertFavoriteRecipes(favorites)
        changeMenuItemColor(item, R.color.yellow)
        snackbar("Recipes saved", binding.detailsLayout)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_LONG
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}