package com.ishdemon.camerascannertest.testUtils

import com.ishdemon.camerascannertest.testUtils.RandomDataUtil.randomPositiveInt
import com.ishdemon.camerascannertest.testUtils.RandomDataUtil.randomString
import com.ishdemon.camerascannertest.data.domain.Album

object TestAlbumDataUtil {

    fun random(): Album {
        return Album(
            id = randomString(),
            album_name = randomString(),
            thumbUri = randomString(),
            count = randomPositiveInt(),
            album_date = randomString()
        )
    }
}