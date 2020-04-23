package fr.cedriccreusot.presentation.list.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.usecases.FetchAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListViewModel(private val useCase: FetchAlbumsUseCase) : ViewModel() {

    val isLoading by lazy {
        MutableLiveData(false)
    }

    val error by lazy {
        MutableLiveData<String>()
    }

    val albums by lazy {
        fetchAlbums()
        MutableLiveData<List<AlbumViewModel>>(emptyList())
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            isLoading.postValue(true)
            withContext(Dispatchers.IO) {
                runCatching {
                    val albumList = useCase.invoke()
                    albums.postValue(albumList.map { album ->
                        AlbumViewModel(album)
                    })
                }.exceptionOrNull()?.let {
                    error.postValue(it.message)
                }
            }
            isLoading.postValue(false)
        }
    }
}