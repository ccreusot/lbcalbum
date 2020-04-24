package fr.cedriccreusot.dataadapter.repositories

import com.google.common.truth.Truth.assertThat
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import fr.cedriccreusot.dataadapter.cache.Cache
import fr.cedriccreusot.dataadapter.mocks.AlbumsServiceMocks
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.repositories.GetAlbumsException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AlbumsRepositoryAdapterTest {
    @Test
    @DisplayName(
        """
        Given the cache is empty
        And the service will fail by throwing an IOException
        When the repository get the track list from it
        Then it should throw a GetAlbumsException
    """
    )
    fun `getting the tracks when the service throw an IOException`() {
        val service = AlbumsServiceMocks.createServiceThatFail()
        val cache = mock<Cache>()
        val repository = AlbumsRepositoryAdapter(service, cache)

        val type = object: TypeToken<List<Album>>() {}.type
        given(cache.get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)).willReturn(null)

        val result = runCatching {
            repository.get()
        }

        verify(cache).get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)
        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(GetAlbumsException::class.java)
    }

    @Test
    @DisplayName(
        """
        Given the cache is empty 
        And the service is working fine
        When the repository get the track list from it
        Then it should return an Album List
        And save that list in the cache
    """
    )
    fun `getting the tracks when the service is working`() {
        val service = AlbumsServiceMocks.createServiceWithResponse()
        val cache = mock<Cache>()
        val repository = AlbumsRepositoryAdapter(service, cache)
        val expectedList = createExpectedAlbumList()

        val type = object: TypeToken<List<Album>>() {}.type
        given(cache.get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)).willReturn(null)

        val result = repository.get()

        verify(cache).get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)
        verify(cache).save(AlbumsRepositoryAdapter.ALBUMS_CACHE, expectedList)
        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    @DisplayName("""
        Given the cache is not empty 
        When the repository get the track list from it
        Then it should return an Album List
    """)
    fun `getting the tracks from the cache`() {
        // Since we can't verify the call on the service, create the one that fail
        // If it's called then the test will fail.
        val service = AlbumsServiceMocks.createServiceThatFail()
        val cache = mock<Cache>()
        val repository = AlbumsRepositoryAdapter(service, cache)
        val expectedList = createExpectedAlbumList()

        val type = object: TypeToken<List<Album>>() {}.type
        given(cache.get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)).willReturn(expectedList)

        val result = repository.get()

        verify(cache).get<List<Album>>(AlbumsRepositoryAdapter.ALBUMS_CACHE, type)
        verifyNoMoreInteractions(cache)
        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo(expectedList)
    }

    private fun createExpectedAlbumList() = listOf(
        Album(
            "1", listOf(
                Track(
                    "1",
                    "accusamus beatae ad facilis cum similique qui sunt",
                    "https://via.placeholder.com/600/92c952",
                    "https://via.placeholder.com/150/92c952"
                )
            )
        ),
        Album(
            "2", listOf(
                Track(
                    "51",
                    "non sunt voluptatem placeat consequuntur rem incidunt",
                    "https://via.placeholder.com/600/8e973b",
                    "https://via.placeholder.com/150/8e973b"
                )
            )
        ),
        Album(
            "3", listOf(
                Track(
                    "101",
                    "incidunt alias vel enim",
                    "https://via.placeholder.com/600/e743b",
                    "https://via.placeholder.com/150/e743b"
                )
            )
        )
    )
}