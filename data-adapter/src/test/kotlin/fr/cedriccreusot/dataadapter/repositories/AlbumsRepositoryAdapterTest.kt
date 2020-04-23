package fr.cedriccreusot.dataadapter.repositories

import com.google.common.truth.Truth.assertThat
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
        Given the service will fail by throwing an IOException
        When the repository get the track list from it
        Then it should throw a GetAlbumsException
    """
    )
    fun `getting the tracks when the service throw an IOException`() {
        val service = AlbumsServiceMocks.createServiceThatFail()
        val repository = AlbumsRepositoryAdapter(service)

        val result = runCatching {
            repository.get()
        }

        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(GetAlbumsException::class.java)
    }

    @Test
    @DisplayName(
        """
        Given the service is working fine
        When the repository get the track list from it
        Then it should return an Album List
    """
    )
    fun `getting the tracks when the service is working`() {
        val service = AlbumsServiceMocks.createServiceWithResponse()
        val repository = AlbumsRepositoryAdapter(service)
        val expectedList = listOf(
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

        val result = repository.get()

        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo(expectedList)
    }
}