package com.stockbit.repository

import android.accounts.NetworkErrorException
import com.stockbit.model.CryptoCompare
import com.stockbit.remote.CryptoCompareDataSource
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

interface CryptoCompareRepository {
    fun fetchTotalTopTierVolume(limit: Int, page: Int, currencySymbol: String): Flow<Resource<CryptoCompare>>
}

class CryptoCompareRepositoryImpl(
    private val remoteDataSource: CryptoCompareDataSource
) : CryptoCompareRepository {

    override fun fetchTotalTopTierVolume(
        limit: Int,
        page: Int,
        currencySymbol: String
    ): Flow<Resource<CryptoCompare>> {
        return flow {
            emit(Resource.loading(null))
            val response = remoteDataSource.fetchTotalTopTierVolume(limit, page, currencySymbol)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(NetworkErrorException(response.message()), null))
            }
        }.catch { e ->
            emit(Resource.error(e, null))
        }
    }
}
