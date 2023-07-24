package dev.soul.kattabozor.domain.model

import dev.soul.kattabozor.domain.model.offers.OfferModel

data class MainModel(
    val offers: List<OfferModel>
)