package com.brunofelixdev.currencyconverter.data.api

interface CurrencyRepository {
    suspend fun fetchRates(request: CurrencyRequest): Resource<CurrencyDto>
}