package com.brunofelixdev.currencyconverter.util

import java.text.NumberFormat
import java.util.*

fun currencyFormat(currency: Double? = 0.0, symbol: String? = "USD"): String {
    val nf = NumberFormat.getCurrencyInstance()
    nf.maximumFractionDigits = 0
    nf.currency = Currency.getInstance(symbol)
    return nf.format(currency)
}