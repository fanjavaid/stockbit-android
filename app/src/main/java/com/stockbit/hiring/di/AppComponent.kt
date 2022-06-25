package com.stockbit.hiring.di

import com.stockbit.hiring.feature_home.di.homeModule
import com.stockbit.local.di.localModule
import com.stockbit.remote.di.createRemoteModule
import com.stockbit.repository.di.repositoryModule

val appComponent = listOf(
    createRemoteModule("https://min-api.cryptocompare.com/"),
    repositoryModule,
    localModule,
    homeModule
)
