package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.repositories.AlbumsRepository

interface FetchAlbumsUseCase {
    @Throws(FetchAlbumsException::class)
    fun invoke(): List<Album>

    companion object {
        fun create(repository: AlbumsRepository): FetchAlbumsUseCase {
            return FetchAlbumsUseCaseImpl(repository)
        }
    }
}

class FetchAlbumsException(override val message: String? = null) : Exception()

internal class FetchAlbumsUseCaseImpl(private val repository: AlbumsRepository) :
    FetchAlbumsUseCase {
    override fun invoke(): List<Album> {
        runCatching() {
            return repository.get()
        }.exceptionOrNull()?.let {
            throw FetchAlbumsException(it.message)
        }
        return emptyList()
    }
}