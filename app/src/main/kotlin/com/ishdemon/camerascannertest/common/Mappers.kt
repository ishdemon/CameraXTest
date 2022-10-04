package com.ishdemon.camerascannertest.common

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}

interface Mapper<From, To> {

    fun mapFrom(from: From): To
}