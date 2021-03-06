package com.danieltifui.recipesapp.adapter.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.danieltifui.recipesapp.R
import com.danieltifui.recipesapp.models.Result
import com.danieltifui.recipesapp.ui.fragmets.favorites.FavoriteRecipesFragmentDirections
import com.danieltifui.recipesapp.ui.fragmets.recipes.ui.RecipesFragmentDirections
import com.danieltifui.recipesapp.untils.Constants.Companion.FAVORITE_LAYOUT_TAG
import com.danieltifui.recipesapp.untils.Constants.Companion.RECIPES_LAYOUT_TAG
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowBindingAdapter {

    companion object {

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, isVegan: Boolean) {
            if (isVegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("onRecipesClickListener")
        @JvmStatic
        fun onRecipesClickListener(recipesRowLayout: ConstraintLayout, result: Result) {
            Log.d(TAG_FRAGMENT, "onRecipesClickListener: CALLED")
            recipesRowLayout.setOnClickListener {
                when (recipesRowLayout.tag) {
                    RECIPES_LAYOUT_TAG -> {
                        val action =
                            RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                        Log.d("rowClick", "onRecipesClickListener: $action")
                        recipesRowLayout.findNavController().navigate(action)
                    }
                    FAVORITE_LAYOUT_TAG -> {
                        val action =
                            FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                                result
                            )
                        recipesRowLayout.findNavController().navigate(action)
                    }
                }
//                try {
//                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
//                          Log.d("rowClick", "onRecipesClickListener: $action")
//                          recipesRowLayout.findNavController().navigate(action)
//                    return@setOnClickListener
//                } catch (e: Exception) {
//                    Log.d("rowClick", "onRecipesClickListener: $e")
//                }
//                try {
//                    val action =
//                        FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
//                            result
//                        )
//                    recipesRowLayout.findNavController().navigate(action)
//                    return@setOnClickListener
//                } catch (e: Exception) {
//                    Log.d("rowClick", "onRecipesClickListener: $e")
//                }
            }
        }


        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

    }
}