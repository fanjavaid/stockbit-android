package com.stockbit.repository

import android.accounts.NetworkErrorException
import com.stockbit.local.datasource.LocalDataSource
import com.stockbit.model.CoinInfo
import com.stockbit.model.CryptoCompare
import com.stockbit.model.Data
import com.stockbit.model.Display
import com.stockbit.model.UsdCurrency
import com.stockbit.model.cache.CoinInfoCache
import com.stockbit.remote.CryptoCompareDataSource
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

interface CryptoCompareRepository {
    fun fetchTotalTopTierVolume(
        limit: Int,
        page: Int,
        currencySymbol: String
    ): Flow<Resource<CryptoCompare>>
}

class CryptoCompareRepositoryImpl(
    private val remoteDataSource: CryptoCompareDataSource,
    private val localDataSource: LocalDataSource<CoinInfoCache>,
    private val dispatchers: AppDispatchers
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
                storeTocache(response.body())
            } else {
                cache(
                    onCache = {
                        emit(Resource.success(it.toResponseBody()))
                    },
                    onEmptyCache = {
                        emit(Resource.error(NetworkErrorException(response.message()), null))
                    }
                )
            }
        }.catch { e ->
            cache(
                onCache = {
                    emit(Resource.success(it.toResponseBody()))
                },
                onEmptyCache = {
                    emit(Resource.error(e, null))
                }
            )
        }
    }

    private suspend fun cache(
        onCache: suspend (List<CoinInfoCache>) -> Unit,
        onEmptyCache: suspend () -> Unit
    ) {
        val cachedData = loadFromCache()
        if (cachedData.isNullOrEmpty()) {
            onEmptyCache.invoke()
        } else {
            onCache.invoke(cachedData)
        }
    }

    private suspend fun storeTocache(cryptoCompare: CryptoCompare?) {
        if (cryptoCompare == null) return
        withContext(dispatchers.io) {
            localDataSource.insertAll(
                cryptoCompare.data.map {
                    CoinInfoCache(
                        id = it.coinInfo.id,
                        name = it.coinInfo.name,
                        fullName = it.coinInfo.fullName,
                        price = it.display.usdCurrency?.price,
                        changeHour = it.display.usdCurrency?.changeHour,
                        changePercentageHour = it.display.usdCurrency?.changePercentageHour
                    )
                }
            )
        }
    }

    private suspend fun loadFromCache(): List<CoinInfoCache> {
        return withContext(dispatchers.io) {
            localDataSource.getAll()
        }
    }

    private fun List<CoinInfoCache>.toResponseBody(): CryptoCompare {
        return CryptoCompare(
            data = this.map {
                Data(
                    coinInfo = CoinInfo(id = it.id, name = it.name, fullName = it.fullName),
                    display = Display(
                        usdCurrency = UsdCurrency(
                            price = it.price,
                            changeHour = it.changeHour,
                            changePercentageHour = it.changePercentageHour
                        )
                    )
                )
            }
        )
    }
}
