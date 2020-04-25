package fr.cedriccreusot.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.repositories.AlbumsRepository
import fr.cedriccreusot.domain.repositories.GetAlbumsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FetchAlbumsUseCaseImplTest {

    private lateinit var repository: AlbumsRepository
    private lateinit var useCase: FetchAlbumsUseCase

    @BeforeEach
    internal fun setUp() {
        repository = mock()
        useCase = FetchAlbumsUseCase.create(repository)
    }

    @Test
    @DisplayName("""
        Given the repository will throw an Exception
        When the use case fetch the album list
        Then the use case should throw FetchAlbumsException with the error message associated.
    """)
    fun `use case invoke should throw an exception when repository throw one`() {
        val error = "An error occurred"

        given(repository.get()).willThrow(GetAlbumsException(error))

        val result = runCatching {
            useCase.invoke()
        }

        verify(repository).get()
        assertThat(result.exceptionOrNull()).isNotNull()
        assertThat(result.exceptionOrNull()).isInstanceOf(FetchAlbumsException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo(error)
    }

    @Test
    @DisplayName("""
        Given the repository will return an album list
        When the use case fetch the album list
        Then the use case should return the album list from the repository as is.
    """)
    fun `use case invoke should return a list when repository return one`() {
        val expectedList = listOf(
            Album("1", emptyList()),
            Album("2", emptyList()),
            Album("3", emptyList())
        )

        given(repository.get()).willReturn(expectedList)

        val result = runCatching {
            useCase.invoke()
        }

        verify(repository).get()
        assertThat(result.exceptionOrNull()).isNull()
        assertThat(result.getOrNull()).isNotNull()
        assertThat(result.getOrNull()).isEqualTo(expectedList)
    }
}