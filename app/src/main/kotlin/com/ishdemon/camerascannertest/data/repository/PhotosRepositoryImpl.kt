package com.ishdemon.camerascannertest.data.repository

import com.ishdemon.camerascannertest.data.database.dao.AlbumDao
import com.ishdemon.camerascannertest.data.database.dao.ImagesDao
import com.ishdemon.camerascannertest.data.entity.AlbumEntity
import com.ishdemon.camerascannertest.data.entity.ImageEntity
import com.ishdemon.camerascannertest.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val imagesDao: ImagesDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): PhotosRepository  {

    override suspend fun addAlbum(album: AlbumEntity) {
        withContext(ioDispatcher) {
            albumDao.insertAlbum(album)
        }
    }

    override fun getAlbums(): Flow<List<AlbumEntity>> {
        return albumDao.getAlbums()
    }

    override suspend fun deleteAlbum(album: AlbumEntity) {
        withContext(ioDispatcher) {
            albumDao.deleteAlbum(album)
        }
    }

    override suspend fun addImage(image: ImageEntity) {
        withContext(ioDispatcher) {
            imagesDao.insertImage(image)
        }
    }

    override fun getImages(albumId: String): Flow<List<ImageEntity>> {
        return imagesDao.getImages(albumId)
    }

    override suspend fun deleteImage(image: ImageEntity) {
        withContext(ioDispatcher) {
            imagesDao.deleteImage(image)
        }
    }

    override suspend fun updateAlbum(album: AlbumEntity) {
        withContext(ioDispatcher) {
            albumDao.updateAlbum(album)
        }
    }
}