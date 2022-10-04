package com.ishdemon.camerascannertest.data.domain

import com.ishdemon.camerascannertest.data.entity.ImageEntity

data class Image(
    val fileName: String,
    val album: String,
    val uri: String,
    val timeStamp: String
) {

    fun toEntity() = ImageEntity(
        fileName = fileName,
        albumId = album,
        timeStamp = timeStamp,
        uri = uri
    )
}