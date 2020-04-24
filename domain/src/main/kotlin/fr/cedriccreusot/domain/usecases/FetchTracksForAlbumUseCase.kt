package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.repositories.AlbumsRepository

interface FetchTracksForAlbumUseCase {

    @Throws(FetchTracksException::class)
    fun invoke(albumId: String) : List<Track>

    companion object {
        fun create(repository: AlbumsRepository): FetchTracksForAlbumUseCase {
            return FetchTracksForAlbumUseCaseImpl(repository)
        }
    }
}

class FetchTracksException(override val message: String? = null): Exception()

internal class FetchTracksForAlbumUseCaseImpl(private val repository: AlbumsRepository) : FetchTracksForAlbumUseCase {
    override fun invoke(albumId: String): List<Track> {
        if (albumId.isEmpty()) {
            throw FetchTracksException("Error can not have an empty Album ID")
        }
        runCatching {
            return repository.get().find { it.id == albumId }?.tracks ?: throw FetchTracksException("Album not found")
        }.exceptionOrNull()?.let {
            throw FetchTracksException(it.message)
        }
        return emptyList()
    }
}