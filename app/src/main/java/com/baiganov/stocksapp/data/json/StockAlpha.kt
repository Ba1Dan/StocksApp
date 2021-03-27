package com.baiganov.stocksapp.data.json

import com.google.gson.annotations.SerializedName


data class StockAlpha (
    @SerializedName("Symbol")
    val symbol: String ,
    @SerializedName("AssetType")
    val assetType: String  ,
    @SerializedName("Name")
    val name: String  = "",
    @SerializedName("Description")
    val description: String = "",
    @SerializedName("Exchange")
    val exchange: String  = "",
    @SerializedName("Currency")
    val currency: String  = "",
    @SerializedName("Country")
    val country: String  = "" ,
    @SerializedName("Sector")
    val sector: String  = "",
    @SerializedName("Industry")
    val industry: String  = "",
    @SerializedName("Address")
    val address: String  = "",
    @SerializedName("FullTimeEmployees")
    val fullTimeEmployees: Int = 0,
    @SerializedName("FiscalYearEnd")
    val fiscalYearEnd: String  = "",
    @SerializedName("LatestQuarter")
    val latestQuarter: String  = "",
    @SerializedName("MarketCapitalization")
    val marketCapitalization: Long = 0,
    @SerializedName("EBITDA")
    val eBITDA: Long,
    @SerializedName("PERatio")
    val pERatio: Double = 0.0,
    @SerializedName("PEGRatio")
    val pEGRatio: Double = 0.0,
    @SerializedName("BookValue")
    val bookValue: Double = 0.0,
    @SerializedName("DividendYield")
    val dividendYield: Double = 0.0,
    @SerializedName("EPS")
    val ePS: Double = 0.0,
    @SerializedName("RevenuePerShareTTM")
    val revenuePerShareTTM: Double = 0.0,
    @SerializedName("ProfitMargin")
    val profitMargin: Double = 0.0,
    @SerializedName("OperatingMarginTTM")
    val operatingMarginTTM: Double = 0.0,
    @SerializedName("ReturnOnAssetsTTM")
    val returnOnAssetsTTM: Double = 0.0,
    @SerializedName("ReturnOnEquityTTM")
    val returnOnEquityTTM: Double = 0.0,
    @SerializedName("RevenueTTM")
    val revenueTTM: Long,
    @SerializedName("GrossProfitTTM")
    val grossProfitTTM: Long,
    @SerializedName("DilutedEPSTTM")
    val dilutedEPSTTM: Double = 0.0,
    @SerializedName("QuarterlyEarningsGrowthYOY")
    val quarterlyEarningsGrowthYOY: Double = 0.0,
    @SerializedName("QuarterlyRevenueGrowthYOY")
    val quarterlyRevenueGrowthYOY: Double = 0.0,
    @SerializedName("AnalystTargetPrice")
    val analystTargetPrice: Double = 0.0,
    @SerializedName("TrailingPE")
    val trailingPE: Double = 0.0,
    @SerializedName("ForwardPE")
    val forwardPE: Double = 0.0,
    @SerializedName("PriceToSalesRatioTTM")
    val priceToSalesRatioTTM: Double = 0.0,
    @SerializedName("PriceToBookRatio")
    val priceToBookRatio: Double = 0.0,
    @SerializedName("EVToRevenue")
    val eVToRevenue: Double = 0.0,
    @SerializedName("EVToEBITDA")
    val eVToEBITDA: Double = 0.0,
    @SerializedName("Beta")
    val beta: Double = 0.0,
    @SerializedName("52WeekHigh")
    val WeekHigh52: Double = 0.0,
    @SerializedName("52WeekLow")
    val WeekLow52: Double = 0.0,
    @SerializedName("50DayMovingAverage")
    val DayMovingAverage50: Double = 0.0,
    @SerializedName("200DayMovingAverage")
    val DayMovingAverage200: Double = 0.0,
    @SerializedName("SharesOutstanding")
    val sharesOutstanding: Long ,
    @SerializedName("SharesFloat")
    val sharesFloat: Long = 0,
    @SerializedName("SharesShort")
    val sharesShort: Long = 0,
    @SerializedName("SharesShortPriorMonth")
    val sharesShortPriorMonth: Long = 0,
    @SerializedName("ShortRatio")
    val shortRatio: Double = 0.0,
    @SerializedName("ShortPercentOutstanding")
    val shortPercentOutstanding: Double = 0.0,
    @SerializedName("ShortPercentFloat")
    val shortPercentFloat: Double = 0.0,
    @SerializedName("PercentInsiders")
    val percentInsiders: Double = 0.0,
    @SerializedName("PercentInstitutions")
    val percentInstitutions: Double = 0.0,
    @SerializedName("ForwardAnnualDividendRate")
    val forwardAnnualDividendRate: Double  = 0.0,
    @SerializedName("ForwardAnnualDividendYield")
    val forwardAnnualDividendYield: Double = 0.0,
    @SerializedName("PayoutRatio")
    val payoutRatio: Double = 0.0,
    @SerializedName("DividendDate")
    val dividendDate: String = "",
    @SerializedName("ExDividendDate")
    val exDividendDate: String = "",
    @SerializedName("LastSplitFactor")
    val lastSplitFactor: String = "" ,
    @SerializedName("LastSplitDate")
    val lastSplitDate: String = ""
)