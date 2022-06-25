package com.stockbit.hiring.feature_login

import com.stockbit.local.di.localModule
import com.stockbit.repository.di.repositoryModule

val testAppComponent = listOf(
    repositoryModule,
    localModule
)
