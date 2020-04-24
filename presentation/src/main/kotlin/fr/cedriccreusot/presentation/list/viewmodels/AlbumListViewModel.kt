package fr.cedriccreusot.presentation.list.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.usecases.FetchAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListViewModel(private val useCase: FetchAlbumsUseCase) : ViewModel() {

    val isLoading : LiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val error : LiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val albums : LiveData<List<AlbumViewModel>> by lazy {
        fetchAlbums()
        // We must explicit it because the compiler need it. Else it will raise the error below :
        // Type inference failed: Not enough information to infer parameter T in constructor MutableLiveData<T : Any!>(p0: T!)
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
                    error.postValue(it.message ?: "")
                }
            }
            isLoading.postValue(false)
        }
    }

    private fun <T : Any> LiveData<T>.postValue(value: T) {
        (this as MutableLiveData<T>).postValue(value)
    }
}