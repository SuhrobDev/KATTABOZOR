package dev.soul.kattabozor.domain.model.offers

data class OfferModel(
    val attributes: List<AttributeModel>,
    val brand: String,
    val category: String,
    val id: Int,
    val image: ImageModel,
    val merchant: String,
    val name: String
)