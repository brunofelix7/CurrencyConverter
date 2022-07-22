package com.brunofelixdev.currencyconverter.data.api

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("date")
    val date: String?,

    @SerializedName("result")
    val result: Double?,

    @SerializedName("query")
    val query: Query?,

    @SerializedName("info")
    val info: Info?,
) {

    data class Query(
        @SerializedName("from")
        val from: String?,

        @SerializedName("to")
        val to: String?,

        @SerializedName("amount")
        val amount: Double?,
    )

    data class Info(
        @SerializedName("timestamp")
        val timestamp: Long?,

        @SerializedName("rate")
        val rate: Double?,
    )
}

