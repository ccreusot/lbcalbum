package fr.cedriccreusot.presentation.routes

import androidx.navigation.NavController
import fr.cedriccreusot.presentation.list.fragments.AlbumListFragmentDirections

interface Router {
    fun routeToDetail(albumId: String)

    companion object {
        fun create(navController: NavController) : Router {
            return RouterImpl(navController)
        }
    }
}

internal class RouterImpl(private val navController: NavController) : Router {
    override fun routeToDetail(albumId: String) {
        AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailFragment(albumId).also { direction ->
            navController.navigate(direction)
        }
    }
}