package com.stockbit.hiring.feature_home.ui.adapters

sealed class StockStatus {
    object Available : StockStatus()
    object NotAvailable : StockStatus()
}
