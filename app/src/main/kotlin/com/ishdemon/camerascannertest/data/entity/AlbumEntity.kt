package com.ishdemon.camerascannertest.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id : String,

    @ColumnInfo(name = "album_name")
    val album_name : String,

    @ColumnInfo(name = "count")
    val count: Int,
) {
}