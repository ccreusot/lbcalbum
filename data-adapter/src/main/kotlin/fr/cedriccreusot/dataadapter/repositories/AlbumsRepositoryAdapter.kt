package fr.cedriccreusot.dataadapter.repositories

import com.google.gson.reflect.TypeToken
import fr.cedriccreusot.dataadapter.cache.Cache
import fr.cedriccreusot.dataadapter.retrofit.AlbumsService
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.repositories.GetAlbumsException

class AlbumsRepositoryAdapter(
    private val service: AlbumsService,
    private val cache: Cache
) : AlbumsRepository {

    companion object {
        const val ALBUMS_CACHE = "albums"
    }

    override fun get(): List<Album> {
        val type = object : TypeToken<List<Album>>() {}.type
        cache.get<List<Album>>(ALBUMS_CACHE, type)?.let {
            return it
        }

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
            }.also {
                cache.save(ALBUMS_CACHE, it)
            }
        }.exceptionOrNull()?.let {
            throw GetAlbumsException(it.message)
        }
        return emptyList()
    }
}