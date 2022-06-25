package com.stockbit.remote

import com.stockbit.model.CryptoCompare
import retrofit2.Response

class CryptoCompareDataSource(private val service: CryptoCompareService) {

    suspend fun fetchTotalTopTierVolume(limit: Int, currencySymbol: String): Response<CryptoCompare> {
        return service.fetchTotalTopTierVolume(limit, currencySymbol)
    }
}
