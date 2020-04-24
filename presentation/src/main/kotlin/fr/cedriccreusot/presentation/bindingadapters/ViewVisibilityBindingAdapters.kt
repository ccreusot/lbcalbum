package fr.cedriccreusot.presentation.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun isVisible(view: View, visibile: Boolean) {
    view.visibility = if (visibile) View.VISIBLE else View.GONE
}