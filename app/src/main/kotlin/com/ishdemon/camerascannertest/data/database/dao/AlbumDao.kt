package com.ishdemon.camerascannertest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ishdemon.camerascannertest.data.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Query("SELECT * FROM albums")
    fun getAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE album_id=:albumId")
    suspend fun getAlbumById(albumId : String) : AlbumEntity?

    @Delete
    suspend fun deleteAlbum(album: AlbumEntity)

}