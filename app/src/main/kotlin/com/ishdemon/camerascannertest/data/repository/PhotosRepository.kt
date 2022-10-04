package com.ishdemon.camerascannertest.data.repository

import com.ishdemon.camerascannertest.data.entity.AlbumEntity
import com.ishdemon.camerascannertest.data.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun addAlbum(album: AlbumEntity)

    fun getAlbums(): Flow<List<AlbumEntity>>

    suspend fun deleteAlbum(album: AlbumEntity)

    suspend fun addImage(image: ImageEntity)

    fun getImages(albumId: String): Flow<List<ImageEntity>>

    suspend fun deleteImage(image: ImageEntity)
}