package dev.soul.kattabozor.data.repository

import dev.soul.kattabozor.data.common.ResponseHandler
import dev.soul.kattabozor.data.mapper.toModel
import dev.soul.kattabozor.data.remote.MainApi
import dev.soul.kattabozor.domain.model.MainModel
import dev.soul.kattabozor.domain.repository.MainRepository
import dev.soul.kattabozor.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApiService: MainApi
) : MainRepository, ResponseHandler() {

    override suspend fun getOffers(): Flow<Resource<MainModel>> = flow {
        try {
            val response = mainApiService.getOffers()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it.toModel()))
                }
            } else {
                response.errorBody()?.let {
                    emit(Resource.Error("Error message: $it"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("IOException: $e"))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: $e"))
        }
    }


}