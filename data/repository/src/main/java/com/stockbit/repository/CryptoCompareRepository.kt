package com.stockbit.repository

import android.accounts.NetworkErrorException
import com.stockbit.model.CryptoCompare
import com.stockbit.remote.CryptoCompareDataSource
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CryptoCompareRepository {
    fun fetchTotalTopTierVolume(limit: Int, currencySymbol: String): Flow<Resource<CryptoCompare>>
}

class CryptoCompareRepositoryImpl(
    private val remoteDataSource: CryptoCompareDataSource
) : CryptoCompareRepository {

    override fun fetchTotalTopTierVolume(
        limit: Int,
        currencySymbol: String
    ): Flow<Resource<CryptoCompare>> {
        return flow {
            emit(Resource.loading(null))
            try {
                val response = remoteDataSource.fetchTotalTopTierVolume(limit, currencySymbol)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    emit(Resource.error(NetworkErrorException(response.message()), null))
                }
            } catch (e: Exception) {
                emit(Resource.error(e, null))
            }
        }
    }
}
