package com.stockbit.hiring.feature_login.di

import com.stockbit.hiring.feature_login.ui.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}
