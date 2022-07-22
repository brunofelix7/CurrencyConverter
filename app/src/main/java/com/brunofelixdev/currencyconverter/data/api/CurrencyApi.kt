package com.brunofelixdev.currencyconverter.data.api

import com.brunofelixdev.currencyconverter.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface   CurrencyApi {

    @GET("convert")
    @Headers(
        "X-Rapidapi-Key: ${BuildConfig.API_KEY}",
        "X-Rapidapi-Host: ${BuildConfig.API_HOST}",
        "Host: ${BuildConfig.API_HOST}"
    )
    suspend fun fetchRates(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double?
    ): Response<CurrencyDto>

}