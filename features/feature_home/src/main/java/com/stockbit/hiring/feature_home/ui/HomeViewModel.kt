package com.stockbit.hiring.feature_home.ui

import com.stockbit.common.base.BaseViewModel
import com.stockbit.model.CryptoCompare
import com.stockbit.repository.CryptoCompareRepository
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: CryptoCompareRepository) : BaseViewModel() {

    fun getTotalTopTierVolume(): Flow<Resource<CryptoCompare>> {
        return repository.fetchTotalTopTierVolume(LIMIT, CURRENCY)
    }

    companion object {
        const val LIMIT = 50
        const val CURRENCY = "USD"
    }
}
