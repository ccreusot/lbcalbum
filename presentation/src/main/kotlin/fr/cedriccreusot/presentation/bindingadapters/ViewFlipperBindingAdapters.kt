package fr.cedriccreusot.presentation.bindingadapters

import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter

@BindingAdapter("isLoading", "hasError")
fun isLoading(view: ViewFlipper, isLoading: Boolean, hasError: Boolean) {
    view.displayedChild = if (isLoading) { 0 } else if (hasError) { 2 } else { 1 }
}