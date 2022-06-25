package com.stockbit.hiring.feature_login.di

import androidx.navigation.NavDirections
import com.stockbit.hiring.feature_login.navigation.LoginToHomeNavDirections
import com.stockbit.hiring.feature_login.ui.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<NavDirections> { LoginToHomeNavDirections() }
    viewModel { LoginViewModel(get(), get()) }
}
