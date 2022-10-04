package com.ishdemon.camerascannertest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishdemon.camerascannertest.common.DataState
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.data.domain.Image
import com.ishdemon.camerascannertest.data.repository.PhotosRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepositoryImpl
): ViewModel() {

    private val _albumState = MutableStateFlow<DataState<List<Album>>>(DataState.Empty)
    val albumState = _albumState.asStateFlow()

    private val _imagesState = MutableStateFlow<DataState<List<Image>>>(DataState.Empty)
    val imagesState = _imagesState.asStateFlow()

    fun getAlbums() {
        viewModelScope.launch {
            _albumState.emit(Loading)
            repository.getAlbums().collectLatest { entities ->
                _albumState.update { Success(entities.map { it.toAlbum() }) }
            }
        }
    }

    fun getPhotosAsync(albumId: String) {
        viewModelScope.launch {
            _imagesState.emit(Loading)
            repository.getImages(albumId).collectLatest { entities ->
                _imagesState.update { Success(entities.map { it.toImage() }) }
            }
        }
    }
}