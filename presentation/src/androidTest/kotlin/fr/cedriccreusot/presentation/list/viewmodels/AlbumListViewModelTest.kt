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
import org.junit.Before
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

    private lateinit var useCase: FetchAlbumsUseCase
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var hasErrorObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<String>
    private lateinit var albumListObserver: Observer<List<AlbumViewModel>>
    private lateinit var viewModel: AlbumListViewModel

    @Before
    fun setUp() {
        useCase = mock()
        loadingObserver = mock()
        hasErrorObserver = mock()
        errorObserver = mock()
        albumListObserver = mock()
        viewModel = AlbumListViewModel(useCase)
    }

    @Test
    fun testWhenUseCaseThrowingAnException() {
        val error = "An error occurred"

        given(useCase.invoke()).willThrow(FetchAlbumsException(error))

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