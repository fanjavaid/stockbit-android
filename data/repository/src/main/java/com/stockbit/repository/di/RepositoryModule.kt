package com.stockbit.repository.di

import com.stockbit.repository.AppDispatchers
import com.stockbit.repository.CryptoCompareRepository
import com.stockbit.repository.CryptoCompareRepositoryImpl
import com.stockbit.repository.InMemoryUserRepository
import com.stockbit.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory<CryptoCompareRepository> { CryptoCompareRepositoryImpl(get()) }
    factory<UserRepository> { InMemoryUserRepository(get()) }
}
