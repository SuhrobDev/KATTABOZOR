package dev.soul.kattabozor.data.remote.dto.offers

import dev.soul.kattabozor.data.remote.dto.offers.Attribute
import dev.soul.kattabozor.data.remote.dto.offers.Image

data class Offer(
    val attributes: List<Attribute>,
    val brand: String,
    val category: String,
    val id: Int,
    val image: Image,
    val merchant: String,
    val name: String
)