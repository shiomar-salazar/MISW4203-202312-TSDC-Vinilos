package com.example.tsdc_vinilos_equipo6.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tsdc_vinilos_equipo6.models.Comment
import com.example.tsdc_vinilos_equipo6.repositories.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentCreateViewModel(application: Application) : AndroidViewModel(application) {

    private val _comment = MutableLiveData<Comment>()
    private val _commentRepository = CommentsRepository(application)

    var comment: LiveData<Comment>
        get() = _comment

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        comment = MutableLiveData()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun addNewComment(albumId: Int, newComment: Comment): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    _commentRepository.addComment(albumId, newComment)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
            true
        } catch (e: Exception) {
            _eventNetworkError.value = true
            false
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CommentCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}