package fr.cedriccreusot.presentation.list.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.presentation.list.adapters.AlbumsAdapter
import fr.cedriccreusot.presentation.list.viewmodels.AlbumViewModel

@BindingAdapter("albums")
fun albums(recyclerView: RecyclerView, albums: List<AlbumViewModel>) {
    recyclerView.adapter = AlbumsAdapter().apply {
        submitList(albums)
    }
}