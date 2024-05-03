package com.example.myapplication.feature_note.data.data_source.cache

import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.util.EntityMapper
import javax.inject.Inject

class CacheMapper : EntityMapper<NoteCacheEntity,Note> {
    override fun mapToEntity(domainModel: Note) = NoteCacheEntity(
        id = domainModel.id,
        title = domainModel.title,
        content = domainModel.content,
        timeStamp = domainModel.timeStamp,
        color = domainModel.color
    )

    override fun mapFromEntity(entity: NoteCacheEntity) = Note(
        id = entity.id,
        title = entity.title,
        content = entity.content,
        timeStamp = entity.timeStamp,
        color = entity.color
    )
}