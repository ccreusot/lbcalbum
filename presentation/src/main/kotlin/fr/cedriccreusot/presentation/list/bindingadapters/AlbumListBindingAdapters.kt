package fr.cedriccreusot.presentation.list.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.presentation.list.adapters.AlbumsAdapter
import fr.cedriccreusot.presentation.list.viewmodels.AlbumViewModel
import fr.cedriccreusot.presentation.routes.Router

@BindingAdapter("albums", "router")
fun albums(recyclerView: RecyclerView, albums: List<AlbumViewModel>, router: Router) {
    recyclerView.adapter = AlbumsAdapter(router).apply {
        submitList(albums)
    }
}