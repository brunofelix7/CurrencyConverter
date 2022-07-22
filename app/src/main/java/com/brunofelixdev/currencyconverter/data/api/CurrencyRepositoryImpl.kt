package com.brunofelixdev.currencyconverter.data.api

import java.lang.Exception
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {

    override suspend fun fetchRates(request: CurrencyRequest): Resource<CurrencyDto> {
        return try {
            val response = api.fetchRates(request.from, request.to, request.amount)
            val result = response.body()

            if (response.isSuccessful && result != null && result.success == true) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error ocurred")
        }
    }
}