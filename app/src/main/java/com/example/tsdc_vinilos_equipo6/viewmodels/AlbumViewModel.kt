package com.example.tsdc_vinilos_equipo6.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.tsdc_vinilos_equipo6.models.Album
import com.example.tsdc_vinilos_equipo6.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    private val _albumRepository = AlbumRepository(application)
    private val _albumId = albumId

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val data = _albumRepository.refreshAlbumData(_albumId)
                    _album.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId:Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}