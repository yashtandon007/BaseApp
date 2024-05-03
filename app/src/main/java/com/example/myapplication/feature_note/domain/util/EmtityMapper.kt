package com.example.myapplication.feature_note.domain.util

interface EntityMapper<Entity, DomainModel> {

    fun mapToEntity(domainModel: DomainModel): Entity
    fun mapFromEntity(entity: Entity): DomainModel

}