package com.stockbit.hiring.feature_home

import com.stockbit.model.CoinInfo
import com.stockbit.model.CryptoCompare
import com.stockbit.model.Data
import com.stockbit.repository.CryptoCompareRepository
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCryptoCompareRepository : CryptoCompareRepository {
    var data: Resource<CryptoCompare> = Resource.success(null)

    override fun fetchTotalTopTierVolume(
        limit: Int,
        page: Int,
        currencySymbol: String
    ): Flow<Resource<CryptoCompare>> {
        return flow {
            emit(data)
        }
    }

    fun getMockData(): CryptoCompare {
        val data = List(MAX_ITEM) {
            Data(
                CoinInfo(id = "$it", name = "Test-$it", fullName = "Test Desc-$it")
            )
        }
        return CryptoCompare(data = data)
    }

    companion object {
        const val MAX_ITEM = 20
    }
}
