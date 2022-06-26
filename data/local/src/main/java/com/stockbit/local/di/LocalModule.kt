package com.stockbit.local.di

import com.stockbit.local.AppDatabase
import com.stockbit.local.datasource.CryptoCompareLocalDataSource
import com.stockbit.local.datasource.LocalDataSource
import com.stockbit.local.datasource.UserLocalDataSource
import com.stockbit.model.cache.CoinInfoCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATABASE = "DATABASE"

val localModule = module {
    single(named(DATABASE)) { AppDatabase.buildDatabase(androidContext()) }
    factory { (get(named(DATABASE)) as AppDatabase).exampleDao() }
    factory { (get(named(DATABASE)) as AppDatabase).coinInfoCacheDao() }
    factory { UserLocalDataSource() }
    factory<LocalDataSource<CoinInfoCache>> { CryptoCompareLocalDataSource(get()) }
}
