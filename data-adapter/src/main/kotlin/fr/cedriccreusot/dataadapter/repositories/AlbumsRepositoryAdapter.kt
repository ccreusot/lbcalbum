package fr.cedriccreusot.dataadapter.repositories

import fr.cedriccreusot.dataadapter.retrofit.AlbumsService
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.repositories.GetAlbumsException

class AlbumsRepositoryAdapter(private val service: AlbumsService) : AlbumsRepository {
    override fun get(): List<Album> {
        runCatching {
            val response = service.getTracks().execute()
            val albumMap = hashMapOf<Int, MutableList<Track>>()
            response.body()?.map {
                if (!albumMap.containsKey(it.albumId)) {
                    albumMap[it.albumId] = mutableListOf()
                }
                albumMap[it.albumId]?.add(Track(it.id.toString(), it.title, it.url, it.thumbnailUrl))
            }
            return albumMap.map {
                Album(it.key.toString(), it.value)
            }
        }.exceptionOrNull()?.let {
            throw GetAlbumsException(it.message)
        }
        return emptyList()
    }
}