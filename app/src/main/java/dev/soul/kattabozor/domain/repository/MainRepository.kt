package dev.soul.kattabozor.domain.repository

import dev.soul.kattabozor.domain.model.MainModel
import dev.soul.kattabozor.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getOffers(): Flow<Resource<MainModel>>
}