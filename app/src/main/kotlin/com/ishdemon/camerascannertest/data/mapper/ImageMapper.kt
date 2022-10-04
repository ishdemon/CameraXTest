package com.ishdemon.camerascannertest.data.mapper

import com.ishdemon.camerascannertest.common.EntityMapper
import com.ishdemon.camerascannertest.data.domain.Image
import com.ishdemon.camerascannertest.data.entity.ImageEntity

interface ImageEntityMapper: EntityMapper<ImageEntity, Image>

class ImageEntityMapperImpl: ImageEntityMapper {
    override fun mapFromEntity(entity: ImageEntity): Image {
        return with(entity){
            Image(
                fileName,
                album,
                uri,
                timeStamp
            )
        }
    }

    override fun mapToEntity(domainModel: Image): ImageEntity {
        return with(domainModel) {
            ImageEntity(
                fileName,
                album,
                timeStamp,
                uri
            )
        }
    }
}