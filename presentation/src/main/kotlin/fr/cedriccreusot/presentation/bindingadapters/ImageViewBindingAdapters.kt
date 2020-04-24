package fr.cedriccreusot.presentation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("downloadAt")
fun downloadAt(imageView: ImageView, url: String) {
    Picasso.get().load(url).into(imageView)
}