package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.entities.Album

interface AlbumsRepository {
    @Throws(GetAlbumsException::class)
    fun get(): List<Album>
}

class GetAlbumsException(override val message: String? = null) : Exception()