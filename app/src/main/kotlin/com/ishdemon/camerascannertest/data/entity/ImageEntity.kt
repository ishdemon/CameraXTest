package com.ishdemon.camerascannertest.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Images")
data class ImageEntity(
    @PrimaryKey
    @NonNull @ColumnInfo(name = "file_name") val fileName: String,
    @NonNull @ColumnInfo(name = "album_id") val albumId: String,
    @NonNull @ColumnInfo(name = "time_stamp") val timeStamp: String
){

}