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
import org.junit.Before
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

    private lateinit var useCase: FetchTracksForAlbumUseCase
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var hasErrorObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<String>
    private lateinit var trackListObserver: Observer<List<TrackViewModel>>
    private lateinit var viewModel: TrackListViewModel

    @Before
    fun setUp() {
        useCase = mock()
        loadingObserver = mock()
        hasErrorObserver = mock()
        errorObserver = mock()
        trackListObserver = mock()
        viewModel = TrackListViewModel(useCase, "albumId")
    }

    @Test
    fun testWhenUseCaseThrowingAnException() {
        val error = "An error occurred"

        given(useCase.invoke("albumId")).willThrow(FetchTracksException(error))

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
        val tracks = listOf(
            Track("1", "title", "full", "thumbnail")
        )
        val expectedList = tracks.map {
            TrackViewModel(it)
        }

        given(useCase.invoke("albumId")).willReturn(
            tracks
        )

        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.hasError.observeForever(hasErrorObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.tracks.observeForever(trackListObserver)

        verify(trackListObserver).onChanged(emptyList())
        verify(loadingObserver).onChanged(true)
        verify(trackListObserver).onChanged(expectedList)
        verify(loadingObserver).onChanged(false)
        verifyZeroInteractions(errorObserver)
        verifyZeroInteractions(hasErrorObserver)
    }
}