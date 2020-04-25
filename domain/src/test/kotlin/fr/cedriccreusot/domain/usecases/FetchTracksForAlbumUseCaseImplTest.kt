package fr.cedriccreusot.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.repositories.GetAlbumsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FetchTracksForAlbumUseCaseImplTest {

    private lateinit var repository: AlbumsRepository
    private lateinit var useCase: FetchTracksForAlbumUseCase

    @BeforeEach
    internal fun setUp() {
        repository = mock()
        useCase = FetchTracksForAlbumUseCase.create(repository)
    }

    @Test
    @DisplayName("""
        Given an empty albumId
        When the use case fetch the track list
        Then the use case should throw FetchTracksException with "Error can not have an empty Album ID".
    """)
    fun `use case invoke should throw an exception when albumId is empty`() {
        val error = "Error can not have an empty Album ID"

        val result = runCatching {
            useCase.invoke("")
        }

        verifyZeroInteractions(repository)
        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(FetchTracksException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo(error)
    }

    @Test
    @DisplayName("""
        Given the repository will throw an Exception
        When the use case fetch the track list
        Then the use case should throw FetchTracksException with the error message associated.
    """)
    fun `use case invoke should throw an exception when repository throw one`() {
        val error = "An error occurred"

        given(repository.get()).willThrow(GetAlbumsException(error))

        val result = runCatching {
            useCase.invoke("albumId")
        }

        verify(repository).get()
        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(FetchTracksException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo(error)
    }

    @Test
    @DisplayName("""
        Given the repository will return the album list
        When the use case fetch the track list for the albumId
        And the album is not found
        Then the use case should throw FetchTracksException with the error message associated.
    """)
    fun `use case invoke should throw an exception when album not found`() {
        val error = "Album not found"
        val expectedList = listOf(
            Album("1", listOf(
                Track("1", "title", "full", "thumbnail")
            ))
        )
        given(repository.get()).willReturn(expectedList)

        val result = runCatching {
            useCase.invoke("albumId")
        }

        verify(repository).get()
        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(FetchTracksException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo(error)
    }

    @Test
    @DisplayName("""
        Given the repository will return the album list
        When the use case fetch the track list for the albumId
        Then the use case should the track list associated.
    """)
    fun `use case invoke should return a track list`() {
        val expectedList = listOf(
            Album("albumId", listOf(
                Track("1", "title", "full", "thumbnail")
            ))
        )
        given(repository.get()).willReturn(expectedList)

        val result = runCatching {
            useCase.invoke("albumId")
        }

        verify(repository).get()
        assertThat(result.exceptionOrNull()).isNull()
        assertThat(result.getOrNull()).isNotNull()
        assertThat(result.getOrNull()).isEqualTo(expectedList[0].tracks)
    }
}