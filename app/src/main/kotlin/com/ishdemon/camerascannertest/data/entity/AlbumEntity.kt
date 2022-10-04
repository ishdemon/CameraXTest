package com.ishdemon.camerascannertest.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ishdemon.camerascannertest.data.domain.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id : String,

    @ColumnInfo(name = "album_name")
    val album_name : String,

    @ColumnInfo(name = "album_date")
    val album_date : String,

    @ColumnInfo(name = "thumb_uri")
    val thumbUri : String,

    @ColumnInfo(name = "count")
    val count: Int,
) {
    fun toAlbum() = Album(
        id = id,
        album_name = album_name,
        album_date = album_date,
        thumbUri = thumbUri,
        count = count
    )
}