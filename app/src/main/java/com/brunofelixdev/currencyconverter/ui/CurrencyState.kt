package com.brunofelixdev.currencyconverter.ui

import com.brunofelixdev.currencyconverter.data.api.CurrencyDto

sealed class CurrencyState {
    object Initial: CurrencyState()
    object Loading: CurrencyState()
    class Success(val data: CurrencyDto): CurrencyState()
    class Error(val message: String): CurrencyState()
}
