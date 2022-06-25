package com.stockbit.hiring.feature_home.di

import com.stockbit.hiring.feature_home.ui.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}
