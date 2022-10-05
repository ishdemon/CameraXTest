package com.ishdemon.camerascannertest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ishdemon.camerascannertest.data.database.AppDatabase
import com.ishdemon.camerascannertest.data.database.dao.AlbumDao
import com.ishdemon.camerascannertest.data.database.dao.ImagesDao
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.data.domain.Image
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var albumDao: AlbumDao
    private lateinit var imagesDao: ImagesDao

    @Before
    fun setUpDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        albumDao = appDatabase.albumsDao()
        imagesDao = appDatabase.imagesDao()
    }

    @Test
    fun checkAlbumInsert() = runBlocking {
        val albumId = UUID.randomUUID().toString()
        val albumName = "Album1"

        val album = Album(id = albumId, album_name = albumName, thumbUri = "", count = 1, album_date = "1-1-2022")
        albumDao.insertAlbum(album.toEntity())

        val albumById =  albumDao.getAlbumById(albumId = albumId)
        assert(albumById?.album_name == albumName)
    }

    @Test
    fun checkPhotoInsert() = runBlocking {
        val photoId = UUID.randomUUID().toString()
        val fileName = "IMG_202212010"
        val albumName = "Album1"
        val image = Image(fileName = fileName, album = albumName, uri = "", timeStamp = 102088383838)
        imagesDao.insertImage(image.toEntity())
        val imageByAlbum =  imagesDao.getSingleImage(albumId = albumName)
        assert(imageByAlbum?.albumId == albumName)
    }


}