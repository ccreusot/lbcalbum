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
import fr.cedriccreusot.presentation.routes.Router
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
        val router = mock<Router>()
        val loadingObserver = mock<Observer<Boolean>>()
        val hasErrorObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val albumListObserver = mock<Observer<List<AlbumViewModel>>>()

        given(useCase.invoke()).willThrow(FetchAlbumsException(error))

        val viewModel = AlbumListViewModel(useCase, router)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.hasError.observeForever(hasErrorObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.albums.observeForever(albumListObserver)

        verify(albumListObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(true)
        verify(hasErrorObserver).onChanged(true)
        verify(errorObserver).onChanged(error)
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun testWhenUseCaseReturnTheAlbums() {
        val useCase = mock<FetchAlbumsUseCase>()
        val router = mock<Router>()
        val loadingObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val hasErrorObserver = mock<Observer<Boolean>>()
        val albumListObserver = mock<Observer<List<AlbumViewModel>>>()
        val albums = listOf(
            Album(
                "1", listOf(
                    Track("1", "title", "full", "thumbnail")
                )
            )
        )
        val expectedList = albums.map {
            AlbumViewModel(it, router)
        }

        given(useCase.invoke()).willReturn(
            albums
        )

        val viewModel = AlbumListViewModel(useCase, router)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.hasError.observeForever(hasErrorObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.albums.observeForever(albumListObserver)

        verify(albumListObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(true)
        verify(albumListObserver).onChanged(expectedList)
        verify(loadingObserver).onChanged(false)
        verifyZeroInteractions(errorObserver)
        verifyZeroInteractions(hasErrorObserver)
    }
}