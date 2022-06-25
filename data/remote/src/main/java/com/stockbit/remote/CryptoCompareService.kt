package com.stockbit.remote

import com.stockbit.model.CryptoCompare
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareService {

    @GET("data/top/totaltoptiervolfull")
    suspend fun fetchTotalTopTierVolume(
        @Query("limit") limit: Int,
        @Query("tsym") currencySymbol: String,
    ): Response<CryptoCompare>
}
