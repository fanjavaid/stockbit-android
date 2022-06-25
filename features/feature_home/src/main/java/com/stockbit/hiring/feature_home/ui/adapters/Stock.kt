package com.stockbit.hiring.feature_home.ui.adapters

data class Stock(
    val id: String,
    val name: String,
    val desc: String,
    val price: String,
    val percentage: String,
    val status: StockStatus = StockStatus.Available
)
