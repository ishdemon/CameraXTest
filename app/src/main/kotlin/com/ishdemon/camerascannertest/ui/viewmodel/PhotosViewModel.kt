package com.ishdemon.camerascannertest.ui.viewmodel

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishdemon.camerascannertest.common.DataState
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.data.domain.Image
import com.ishdemon.camerascannertest.data.repository.PhotosRepositoryImpl
import com.ishdemon.camerascannertest.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _albumState = MutableStateFlow<DataState<List<Album>>>(DataState.Empty)
    val albumState = _albumState.asStateFlow()

    private val _imagesState = MutableStateFlow<DataState<List<Image>>>(DataState.Empty)
    val imagesState = _imagesState.asStateFlow()

    private val _imageFolderState = MutableStateFlow<DataState<List<File>>>(DataState.Empty)
    val imagesFolderState = _imageFolderState.asStateFlow()



    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch {
            _albumState.emit(Loading)
            repository.getAlbums().collectLatest { entities ->
                _albumState.update { Success(entities.map { it.toAlbum() }) }
            }
        }
    }

    fun addImage(image: Image) {
        viewModelScope.launch {
            repository.addImage(image.toEntity())
        }
    }

    fun addAlbum(album: Album) {
        viewModelScope.launch {
            repository.addAlbum(album.toEntity())
        }
    }

    fun updateAlbum(album: Album) {
        viewModelScope.launch {
            repository.updateAlbum(album.toEntity())
        }
    }

    fun getImages(albumId: String) {
        viewModelScope.launch {
            _imagesState.emit(Loading)
            repository.getImages(albumId).collect { entities ->
                _imagesState.update { Success(entities.map { it.toImage() }) }
            }
        }
    }

    fun readFilesFromAlbum(albumId: String) {
        viewModelScope.launch {
            _imageFolderState.emit(Loading)
            withContext(ioDispatcher){
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/CameraScanner/$albumId"
                Log.d("Files", "Path: $path")
                val directory = File(path)
                val files = directory.listFiles()
                _imageFolderState.emit(Success(files?.toList()?.reversed() ?: emptyList()))
                if (files != null) {
                    Log.d("Files", "Size: " + files.size)
                }
                if (files != null) {
                    for (i in files.indices) {
                        Log.d("Files", "FileName:" + files[i].name)
                    }
                }
            }
        }
    }
}