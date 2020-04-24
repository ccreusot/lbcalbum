package fr.cedriccreusot.presentation.list.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.usecases.FetchAlbumsException
import fr.cedriccreusot.domain.usecases.FetchAlbumsUseCase
import fr.cedriccreusot.presentation.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testWhenUseCaseThrowingAnException() {
        val error = "An error occurred"
        val useCase = mock<FetchAlbumsUseCase>()
        val loadingObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val albumListObserver = mock<Observer<List<AlbumViewModel>>>()

        given(useCase.invoke()).willThrow(FetchAlbumsException(error))

        val viewModel = AlbumListViewModel(useCase)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.albums.observeForever(albumListObserver)

        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(error)
        verify(albumListObserver).onChanged(emptyList())
    }

    @Test
    fun testWhenUseCaseReturnTheAlbums() {
        val useCase = mock<FetchAlbumsUseCase>()
        val loadingObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val albumListObserver = mock<Observer<List<AlbumViewModel>>>()
        val albums = listOf(
            Album(
                "1", listOf(
                    Track("1", "title", "full", "thumbnail")
                )
            )
        )
        val expectedList = albums.map {
            AlbumViewModel(it)
        }

        given(useCase.invoke()).willReturn(
            albums
        )

        val viewModel = AlbumListViewModel(useCase)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.albums.observeForever(albumListObserver)

        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
        verifyZeroInteractions(errorObserver)
        verify(albumListObserver).onChanged(emptyList())
        verify(albumListObserver).onChanged(expectedList)
    }
}