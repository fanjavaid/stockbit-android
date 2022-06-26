package com.stockbit.model

import com.google.gson.annotations.SerializedName

data class CryptoCompare(

    @SerializedName("Message")
    var Message: String? = null,

    @SerializedName("Type")
    var Type: Int? = null,

    @SerializedName("MetaData")
    var MetaData: MetaData? = MetaData(),

    @SerializedName("SponsoredData")
    var SponsoredData: List<String> = listOf(),

    @SerializedName("Data")
    var Data: List<Data> = listOf(),

    @SerializedName("RateLimit")
    var RateLimit: RateLimit? = RateLimit(),

    @SerializedName("HasWarning")
    var HasWarning: Boolean? = null
)

data class MetaData(
    @SerializedName("Count")
    var Count: Int? = null
)

data class Weiss(

    @SerializedName("Rating")
    var Rating: String? = null,

    @SerializedName("TechnologyAdoptionRating")
    var TechnologyAdoptionRating: String? = null,

    @SerializedName("MarketPerformanceRating")
    var MarketPerformanceRating: String? = null
)

data class Rating(
    @SerializedName("Weiss")
    var Weiss: Weiss? = Weiss()
)

data class CoinInfo(
    @SerializedName("Id")
    var Id: String = "",

    @SerializedName("Name")
    var Name: String? = null,

    @SerializedName("FullName")
    var FullName: String? = null,

    @SerializedName("Internal")
    var Internal: String? = null,

    @SerializedName("ImageUrl")
    var ImageUrl: String? = null,

    @SerializedName("Url")
    var Url: String? = null,

    @SerializedName("Algorithm")
    var Algorithm: String? = null,

    @SerializedName("ProofType")
    var ProofType: String? = null,

    @SerializedName("Rating")
    var Rating: Rating? = Rating(),

    @SerializedName("BlockNumber")
    var BlockNumber: Int? = null,

    @SerializedName("BlockTime")
    var BlockTime: Double? = null,

    @SerializedName("BlockReward")
    var BlockReward: Double? = null,

    @SerializedName("AssetLaunchDate")
    var AssetLaunchDate: String? = null,

    @SerializedName("MaxSupply")
    var MaxSupply: Double? = null,

    @SerializedName("Type")
    var Type: Int? = null,

    @SerializedName("DocumentType")
    var DocumentType: String? = null
)

data class USD(

    @SerializedName("TYPE")
    var TYPE: String? = null,
    @SerializedName("MARKET")
    var MARKET: String? = null,
    @SerializedName("FROMSYMBOL")
    var FROMSYMBOL: String? = null,
    @SerializedName("TOSYMBOL")
    var TOSYMBOL: String? = null,
    @SerializedName("FLAGS")
    var FLAGS: String? = null,
    @SerializedName("PRICE")
    var PRICE: String? = null,
//    @SerializedName("LASTUPDATE")
//    var LASTUPDATE: Int? = null,
//    @SerializedName("MEDIAN")
//    var MEDIAN: Int? = null,
//    @SerializedName("LASTVOLUME")
//    var LASTVOLUME: Double? = null,
//    @SerializedName("LASTVOLUMETO")
//    var LASTVOLUMETO: Double? = null,
//    @SerializedName("LASTTRADEID")
//    var LASTTRADEID: String? = null,
//    @SerializedName("VOLUMEDAY")
//    var VOLUMEDAY: Double? = null,
//    @SerializedName("VOLUMEDAYTO")
//    var VOLUMEDAYTO: Double? = null,
//    @SerializedName("VOLUME24HOUR")
//    var VOLUME24HOUR: Double? = null,
//    @SerializedName("VOLUME24HOURTO")
//    var VOLUME24HOURTO: Double? = null,
//    @SerializedName("OPENDAY")
//    var OPENDAY: Double? = null,
//    @SerializedName("HIGHDAY")
//    var HIGHDAY: Double? = null,
//    @SerializedName("LOWDAY")
//    var LOWDAY: Double? = null,
//    @SerializedName("OPEN24HOUR")
//    var OPEN24HOUR: Double? = null,
//    @SerializedName("HIGH24HOUR")
//    var HIGH24HOUR: Double? = null,
//    @SerializedName("LOW24HOUR")
//    var LOW24HOUR: Double? = null,
//    @SerializedName("LASTMARKET")
//    var LASTMARKET: String? = null,
//    @SerializedName("VOLUMEHOUR")
//    var VOLUMEHOUR: Double? = null,
//    @SerializedName("VOLUMEHOURTO")
//    var VOLUMEHOURTO: Double? = null,
//    @SerializedName("OPENHOUR")
//    var OPENHOUR: Double? = null,
//    @SerializedName("HIGHHOUR")
//    var HIGHHOUR: Double? = null,
//    @SerializedName("LOWHOUR")
//    var LOWHOUR: Double? = null,
//    @SerializedName("TOPTIERVOLUME24HOUR")
//    var TOPTIERVOLUME24HOUR: Double? = null,
//    @SerializedName("TOPTIERVOLUME24HOURTO")
//    var TOPTIERVOLUME24HOURTO: Double? = null,
//    @SerializedName("CHANGE24HOUR")
//    var CHANGE24HOUR: Double? = null,
//    @SerializedName("CHANGEPCT24HOUR")
//    var CHANGEPCT24HOUR: Double? = null,
//    @SerializedName("CHANGEDAY")
//    var CHANGEDAY: Double? = null,
//    @SerializedName("CHANGEPCTDAY")
//    var CHANGEPCTDAY: Double? = null,
    @SerializedName("CHANGEHOUR")
    var CHANGEHOUR: String? = null,
    @SerializedName("CHANGEPCTHOUR")
    var CHANGEPCTHOUR: String? = null,
//    @SerializedName("CONVERSIONTYPE")
//    var CONVERSIONTYPE: String? = null,
//    @SerializedName("CONVERSIONSYMBOL")
//    var CONVERSIONSYMBOL: String? = null,
//    @SerializedName("SUPPLY")
//    var SUPPLY: Int? = null,
//    @SerializedName("MKTCAP")
//    var MKTCAP: Int? = null,
//    @SerializedName("MKTCAPPENALTY")
//    var MKTCAPPENALTY: Int? = null,
//    @SerializedName("CIRCULATINGSUPPLY")
//    var CIRCULATINGSUPPLY: Int? = null,
//    @SerializedName("CIRCULATINGSUPPLYMKTCAP")
//    var CIRCULATINGSUPPLYMKTCAP: Int? = null,
//    @SerializedName("TOTALVOLUME24H")
//    var TOTALVOLUME24H: Double? = null,
//    @SerializedName("TOTALVOLUME24HTO")
//    var TOTALVOLUME24HTO: Double? = null,
//    @SerializedName("TOTALTOPTIERVOLUME24H")
//    var TOTALTOPTIERVOLUME24H: Double? = null,
//    @SerializedName("TOTALTOPTIERVOLUME24HTO")
//    var TOTALTOPTIERVOLUME24HTO: Double? = null,
//    @SerializedName("IMAGEURL")
//    var IMAGEURL: String? = null
)

data class RAW(
    @SerializedName("USD")
    var USD: USD? = USD()
)

data class DISPLAY(
    @SerializedName("USD") var USD: USD? = USD()
)

data class Data(
    @SerializedName("CoinInfo") var CoinInfo: CoinInfo = CoinInfo(),
//    @SerializedName("RAW") var RAW: RAW? = RAW(),
    @SerializedName("DISPLAY") var DISPLAY: DISPLAY = DISPLAY()
)

class RateLimit
