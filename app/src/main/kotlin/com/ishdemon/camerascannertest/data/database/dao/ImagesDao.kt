package com.ishdemon.camerascannertest.data.database.dao;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ishdemon.camerascannertest.data.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: ImageEntity)

    @Query("SELECT * FROM images WHERE album_id=:albumId ORDER BY time_stamp DESC")
    fun getImages(albumId : String): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images WHERE album_id=:albumId")
    fun getSingleImage(albumId : String): ImageEntity?

    @Delete
    suspend fun deleteImage(image: ImageEntity)
}