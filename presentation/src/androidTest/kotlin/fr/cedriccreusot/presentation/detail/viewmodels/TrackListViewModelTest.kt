package fr.cedriccreusot.presentation.detail.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import fr.cedriccreusot.domain.entities.Track
import fr.cedriccreusot.domain.usecases.FetchTracksException
import fr.cedriccreusot.domain.usecases.FetchTracksForAlbumUseCase
import fr.cedriccreusot.presentation.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testWhenUseCaseThrowingAnException() {
        val error = "An error occurred"
        val useCase = mock<FetchTracksForAlbumUseCase>()
        val loadingObserver = mock<Observer<Boolean>>()
        val hasErrorObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val trackListObserver = mock<Observer<List<TrackViewModel>>>()

        given(useCase.invoke("albumId")).willThrow(FetchTracksException(error))

        val viewModel = TrackListViewModel(useCase, "albumId")
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.hasError.observeForever(hasErrorObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.tracks.observeForever(trackListObserver)

        verify(trackListObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(true)
        verify(hasErrorObserver).onChanged(true)
        verify(errorObserver).onChanged(error)
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun testWhenUseCaseReturnTheTracks() {
        val useCase = mock<FetchTracksForAlbumUseCase>()
        val loadingObserver = mock<Observer<Boolean>>()
        val errorObserver = mock<Observer<String>>()
        val hasErrorObserver = mock<Observer<Boolean>>()
        val albumListObserver = mock<Observer<List<TrackViewModel>>>()
        val tracks = listOf(
            Track("1", "title", "full", "thumbnail")
        )
        val expectedList = tracks.map {
            TrackViewModel(it)
        }

        given(useCase.invoke("albumId")).willReturn(
            tracks
        )

        val viewModel = TrackListViewModel(useCase, "albumId")
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.hasError.observeForever(hasErrorObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.tracks.observeForever(albumListObserver)

        verify(albumListObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(true)
        verify(albumListObserver).onChanged(expectedList)
        verify(loadingObserver).onChanged(false)
        verifyZeroInteractions(errorObserver)
        verifyZeroInteractions(hasErrorObserver)
    }
}