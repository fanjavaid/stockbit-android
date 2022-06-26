package com.stockbit.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stockbit.model.cache.CoinInfoCache

@Dao
interface CoinInfoCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinInfos: List<CoinInfoCache>)

    @Query("SELECT * FROM CoinInfoCache")
    fun getCoinInfoCaches(): List<CoinInfoCache>
}
