package fr.cedriccreusot.presentation.list.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.cedriccreusot.presentation.databinding.FragmentAlbumListBinding
import fr.cedriccreusot.presentation.list.viewmodels.AlbumListViewModel
import fr.cedriccreusot.presentation.routes.Router
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumListFragment : Fragment() {

    private val albumListViewModel: AlbumListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAlbumListBinding.inflate(inflater, container, false).apply {
            viewModel = albumListViewModel
            router = Router.create(findNavController())
            lifecycleOwner = this@AlbumListFragment
        }.root
    }
}