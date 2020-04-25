package fr.cedriccreusot.presentation.list.viewmodels

import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.presentation.routes.Router

data class AlbumViewModel(private val album: Album) {
    val hasFirstThumbnail: Boolean = album.tracks.isNotEmpty()
    val firstThumbnail: String = if (hasFirstThumbnail) album.tracks[0].thumbnail else ""

    val hasSecondThumbnail: Boolean = album.tracks.size >= 2
    val secondThumbnail: String = if (hasSecondThumbnail) album.tracks[1].thumbnail else ""

    val hasThirdThumbnail: Boolean = album.tracks.size >= 3
    val thirdThumbnail: String = if (hasThirdThumbnail) album.tracks[2].thumbnail else ""

    val hasTrackCount: Boolean = album.tracks.size - 3 > 0
    val trackCount: String = if (hasTrackCount) "+${album.tracks.size - 3}" else ""

    fun openDetail(router: Router) {
        router.routeToDetail(album.id)
    }
}