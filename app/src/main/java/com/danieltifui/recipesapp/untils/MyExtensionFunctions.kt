package com.danieltifui.recipesapp.untils

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.danieltifui.recipesapp.ui.fragmets.bottomsheet.TAG
import com.danieltifui.recipesapp.untils.Constants.Companion.TAG_FRAGMENT
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

inline fun <reified T: View> HorizontalScrollView.scrollToPosition(
    id: Int
) {
    Log.d("RecipesBottomSheet", "scrollToPosition: $id")
    val view = findViewById<T>(id) ?: return
    val leftEdgePx = view.left
    val scrollCenterPx = Resources.getSystem().displayMetrics.widthPixels / 2
    val scrollPx = if (leftEdgePx < scrollCenterPx) 0 else leftEdgePx - scrollCenterPx + view.width / 2
    Log.d(TAG, "scrollToPosition: $scrollCenterPx and id: ${view.left}")
    this.post {
        Log.d(TAG, "scrollToPosition: $scrollPx ${view.top}")
        this.smoothScrollTo(scrollPx, view.top)
    }
}

fun Fragment.snackbar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    ).setAction("Okay") {}.show()
}

fun Activity.snackbar(text: String, view: View) {
    Snackbar.make(
        view,
        text,
        Snackbar.LENGTH_LONG
    ).setAction("Okay") {}.show()
}
