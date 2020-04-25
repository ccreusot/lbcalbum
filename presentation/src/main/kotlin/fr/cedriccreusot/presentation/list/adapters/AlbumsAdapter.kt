package fr.cedriccreusot.presentation.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.presentation.databinding.ItemAlbumListBinding
import fr.cedriccreusot.presentation.list.viewmodels.AlbumViewModel
import fr.cedriccreusot.presentation.routes.Router

class AlbumsAdapter(private val router: Router) : ListAdapter<AlbumViewModel, AlbumsAdapter.AlbumViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(ItemAlbumListBinding.inflate(layoutInflater, parent, false), router)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlbumViewHolder(private val binding : ItemAlbumListBinding, private val router: Router) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumViewModel) {
            binding.viewModel = item
            binding.router = router
        }
    }

    internal class ItemCallback : DiffUtil.ItemCallback<AlbumViewModel>() {
        override fun areItemsTheSame(oldItem: AlbumViewModel, newItem: AlbumViewModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AlbumViewModel, newItem: AlbumViewModel): Boolean {
            return oldItem == newItem
        }
    }

}


