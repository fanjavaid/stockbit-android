package com.stockbit.local.datasource

import com.stockbit.local.dao.CoinInfoCacheDao
import com.stockbit.model.cache.CoinInfoCache

class CryptoCompareLocalDataSource(
    private val dao: CoinInfoCacheDao
) : LocalDataSource<CoinInfoCache> {

    override fun insert(data: CoinInfoCache) {
        TODO("Not yet implemented")
    }

    override fun insertAll(data: List<CoinInfoCache>) {
        dao.insert(data)
    }

    override fun getAll(): List<CoinInfoCache> {
        return dao.getCoinInfoCaches()
    }
}
