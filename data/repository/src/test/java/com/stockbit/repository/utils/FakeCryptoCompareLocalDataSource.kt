package com.stockbit.repository.utils

import com.stockbit.local.datasource.LocalDataSource
import com.stockbit.model.cache.CoinInfoCache

class FakeCryptoCompareLocalDataSource : LocalDataSource<CoinInfoCache> {

    private val coinInfos = mutableListOf<CoinInfoCache>()

    override fun insert(data: CoinInfoCache) {
        TODO("Not yet implemented")
    }

    override fun insertAll(data: List<CoinInfoCache>) {
        coinInfos.addAll(data)
    }

    override fun getAll(): List<CoinInfoCache> {
        return coinInfos
    }

    fun clear() = coinInfos.clear()
}
