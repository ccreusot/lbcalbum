package fr.cedriccreusot.presentation.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.presentation.databinding.ItemTrackListBinding
import fr.cedriccreusot.presentation.detail.viewmodels.TrackViewModel

class TracksAdapter : ListAdapter<TrackViewModel, TracksAdapter.TrackViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrackViewHolder(ItemTrackListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrackViewHolder(private val binding : ItemTrackListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackViewModel) {
            binding.viewModel = item
        }
    }

    internal class ItemCallback : DiffUtil.ItemCallback<TrackViewModel>() {
        override fun areItemsTheSame(oldItem: TrackViewModel, newItem: TrackViewModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TrackViewModel, newItem: TrackViewModel): Boolean {
            return oldItem == newItem
        }
    }

}
