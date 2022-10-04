package com.ishdemon.camerascannertest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ishdemon.camerascannertest.data.domain.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: Album)

    @Query("SELECT * FROM albums")
    fun getAlbums(): Flow<List<Album>>

    @Query("SELECT * FROM albums WHERE album_id=:albumId")
    suspend fun getAlbumById(albumId : String) : Album?

    @Delete
    suspend fun deleteAlbum(album: Album)

}