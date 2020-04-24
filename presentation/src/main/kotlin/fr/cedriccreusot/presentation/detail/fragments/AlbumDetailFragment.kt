package fr.cedriccreusot.presentation.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import fr.cedriccreusot.presentation.databinding.FragmentAlbumDetailBinding
import fr.cedriccreusot.presentation.detail.viewmodels.TrackListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AlbumDetailFragment : Fragment() {
    private val args: AlbumDetailFragmentArgs by navArgs()
    private val trackListViewModel: TrackListViewModel by viewModel { parametersOf(args.albumId)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAlbumDetailBinding.inflate(inflater, container, false).apply {
            viewModel = trackListViewModel
            lifecycleOwner = this@AlbumDetailFragment
        }.root
    }
}