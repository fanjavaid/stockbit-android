package com.stockbit.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinInfoCache(
    @PrimaryKey
    var id: String,
    var name: String? = null,
    var fullName: String? = null,
    var price: String? = null,
    var changeHour: String? = null,
    var changePercentageHour: String? = null
)
