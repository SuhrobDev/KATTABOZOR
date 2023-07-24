package dev.soul.kattabozor.data.remote

import dev.soul.kattabozor.data.remote.dto.offers.MainDto
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    /************************************************** OFFERS ******************************/

    @GET("hh/test/api/v1/offers")
    suspend fun getOffers(): Response<MainDto>

}
