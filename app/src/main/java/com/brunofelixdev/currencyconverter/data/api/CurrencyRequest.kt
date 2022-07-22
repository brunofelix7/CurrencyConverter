package com.brunofelixdev.currencyconverter.data.api

import com.google.gson.annotations.SerializedName

data class CurrencyRequest(
    @SerializedName("from")
    val from: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("amount")
    val amount: Double?,
)