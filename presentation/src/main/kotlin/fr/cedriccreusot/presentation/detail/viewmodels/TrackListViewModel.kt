package fr.cedriccreusot.presentation.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.usecases.FetchTracksForAlbumUseCase
import fr.cedriccreusot.presentation.extensions.postValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackListViewModel(private val useCase: FetchTracksForAlbumUseCase, private val albumId: String) : ViewModel() {

    val isLoading: LiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val hasError: LiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val error: LiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val tracks: LiveData<List<TrackViewModel>> by lazy {
        fetchTracks()
        MutableLiveData<List<TrackViewModel>>(emptyList())
    }

    private fun fetchTracks() {
        viewModelScope.launch {
            isLoading.postValue(true)
            withContext(Dispatchers.IO) {
                runCatching {
                    val trackList = useCase.invoke(albumId)
                    tracks.postValue(trackList.map { track ->
                        TrackViewModel(track)
                    })
                }.exceptionOrNull()?.let {
                    hasError.postValue(true)
                    error.postValue(it.message ?: "")
                }
            }
            isLoading.postValue(false)
        }
    }
}