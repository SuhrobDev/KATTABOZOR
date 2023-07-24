package dev.soul.kattabozor.data.remote.dto.offers

data class OfferDto(
    val attributes: List<AttributeDto>,
    val brand: String,
    val category: String,
    val id: Int,
    val image: ImageDto,
    val merchant: String,
    val name: String
)