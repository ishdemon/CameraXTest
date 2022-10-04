package com.ishdemon.camerascannertest.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.ishdemon.camerascannertest.data.domain.Image;
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(image: Image)

    @Query("SELECT * FROM images WHERE album_id=:albumId ORDER BY time_stamp DESC")
    fun getPhotos(albumId : String): Flow<List<Image>>

    @Query("SELECT * FROM images WHERE album_id=:albumId")
    fun getSinglePhoto(albumId : String): Image?

    @Delete
    suspend fun deletePhoto(image: Image)
}