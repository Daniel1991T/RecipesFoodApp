package com.danieltifui.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_DETAILS_ACTIVITY
import com.danieltifui.recipesapp.untils.Resource
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

    private var isFavoriteRecipes = false

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
        detailsFavoritesViewModel.getIsFavoriteStatus(args.result.id ?: -1)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        detailsFavoritesViewModel.isFavoriteStatus.observe(this, { isFavorite ->
            Log.d("test", "checkSavedRecipes: ${args.result.id} and ${isFavorite.message}")
            when(isFavorite) {
                is Resource.Success -> {
                    if (isFavorite.data == args.result.id) {
                        isFavoriteRecipes = true
                        changeMenuItemColor(menuItem, R.color.yellow)
                    } else {
                        isFavoriteRecipes = false
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
                is Resource.Error -> {
                    Log.d("test", "checkSavedRecipes: ")
                    changeMenuItemColor(menuItem, R.color.white)
                }
                else -> return@observe
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_save_favorite_menu, menu)
        menuItem = menu!!.findItem(R.id.save_favorite_menu)
        checkSavedRecipes(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_favorite_menu && !isFavoriteRecipes) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_favorite_menu && isFavoriteRecipes) {
            removeToFavorite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeToFavorite(item: MenuItem) {
        val favorites = args.result
        detailsFavoritesViewModel.deleteFromFavoriteRecipes(favorites)
        changeMenuItemColor(menuItem, R.color.white)
        snackbar("Recipes delete from favorite!", binding.detailsLayout)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favorites = args.result
        detailsFavoritesViewModel.insertFavoriteRecipes(favorites)
        changeMenuItemColor(item, R.color.yellow)
        snackbar("Recipes saved", binding.detailsLayout)
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}