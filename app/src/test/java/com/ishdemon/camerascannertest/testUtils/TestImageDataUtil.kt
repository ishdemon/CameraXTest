package com.ishdemon.camerascannertest.testUtils

import com.ishdemon.camerascannertest.data.domain.Image
import com.ishdemon.camerascannertest.testUtils.RandomDataUtil.randomLong
import com.ishdemon.camerascannertest.testUtils.RandomDataUtil.randomString

object TestImageDataUtil {

    fun random(): Image {
        return Image(
            fileName = randomString(),
            album = randomString(),
            uri = randomString(),
            timeStamp = randomLong()
        )
    }
}