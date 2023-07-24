package dev.soul.kattabozor.data.mapper

import dev.soul.kattabozor.data.remote.dto.offers.AttributeDto
import dev.soul.kattabozor.data.remote.dto.offers.ImageDto
import dev.soul.kattabozor.data.remote.dto.offers.MainDto
import dev.soul.kattabozor.data.remote.dto.offers.OfferDto
import dev.soul.kattabozor.domain.model.MainModel
import dev.soul.kattabozor.domain.model.offers.AttributeModel
import dev.soul.kattabozor.domain.model.offers.ImageModel
import dev.soul.kattabozor.domain.model.offers.OfferModel

fun OfferDto.toModel(): OfferModel {
    return OfferModel(
        attributes = attributes.map { it.toModel() },
        brand = brand,
        category = category,
        id = id,
        image = image.toModel(),
        merchant = merchant,
        name = name
    )
}

fun ImageDto.toModel(): ImageModel {
    return ImageModel(height = height, url = url, width = width)
}

fun AttributeDto.toModel(): AttributeModel {
    return AttributeModel(name = name, value = value)
}

fun MainDto.toModel(): MainModel {
    return MainModel(offers = offers.map { it.toModel() })
}