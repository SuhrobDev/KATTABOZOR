package dev.soul.kattabozor.domain.usecase.offers

import dev.soul.kattabozor.domain.model.MainModel
import dev.soul.kattabozor.domain.repository.MainRepository
import dev.soul.kattabozor.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OffersUseCase @Inject constructor(private val repository: MainRepository) {

    suspend operator fun invoke(): Flow<Resource<MainModel>> {
        return repository.getOffers()
    }
}