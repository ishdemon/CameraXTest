package com.ishdemon.camerascannertest.data.domain

import com.ishdemon.camerascannertest.data.entity.AlbumEntity

data class Album(
    val id: String,
    val album_name: String,
    val thumbUri: String,
    val count: Int
) {
    fun toEntity() = AlbumEntity(
        id = id,
        album_name = album_name,
        thumbUri = thumbUri,
        count = count
    )
}