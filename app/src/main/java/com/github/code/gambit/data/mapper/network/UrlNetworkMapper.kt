package com.github.code.gambit.data.mapper.network

import com.github.code.gambit.EntityMapper
import com.github.code.gambit.data.entity.network.UrlNetworkEntity
import com.github.code.gambit.data.model.Url
import javax.inject.Inject

class UrlNetworkMapper
@Inject
constructor() : EntityMapper<UrlNetworkEntity, Url> {

    override fun mapFromEntity(entity: UrlNetworkEntity): Url {
        return Url(
            id = entity.gs1_pk,
            file = entity.pk,
            timestamp = entity.sk.split("#")[1],
            visible = entity.visible
        )
    }

    override fun mapToEntity(domainModel: Url): UrlNetworkEntity {
        return UrlNetworkEntity(
            pk = domainModel.file,
            sk = "URL#${domainModel.timestamp}",
            gs1_pk = domainModel.id,
            visible = domainModel.visible
        )
    }

    override fun mapFromEntityList(entities: List<UrlNetworkEntity>): List<Url> {
        return entities.map { mapFromEntity(it) }
    }
}