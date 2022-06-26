package com.stockbit.model

import com.google.gson.annotations.SerializedName

data class CryptoCompare(
    @SerializedName("Data")
    var data: List<Data> = listOf()
)

data class Data(
    @SerializedName("CoinInfo")
    var coinInfo: CoinInfo = CoinInfo(),

    @SerializedName("DISPLAY")
    var display: Display = Display()
)

data class CoinInfo(
    @SerializedName("Id")
    var id: String = "",

    @SerializedName("Name")
    var name: String? = null,

    @SerializedName("FullName")
    var fullName: String? = null
)

data class Display(
    @SerializedName("USD")
    var usdCurrency: UsdCurrency? = UsdCurrency()
)

data class UsdCurrency(
    @SerializedName("PRICE")
    var price: String? = null,

    @SerializedName("CHANGEHOUR")
    var changeHour: String? = null,

    @SerializedName("CHANGEPCTHOUR")
    var changePercentageHour: String? = null
)
