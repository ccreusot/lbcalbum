package fr.cedriccreusot.presentation.detail.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.presentation.detail.adapters.TracksAdapter
import fr.cedriccreusot.presentation.detail.viewmodels.TrackViewModel

@BindingAdapter("tracks")
fun tracks(recyclerView: RecyclerView, albums: List<TrackViewModel>) {
    recyclerView.adapter = TracksAdapter().apply {
        submitList(albums)
    }
}