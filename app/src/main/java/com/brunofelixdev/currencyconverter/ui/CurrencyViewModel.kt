package com.brunofelixdev.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunofelixdev.currencyconverter.data.api.CurrencyRequest
import com.brunofelixdev.currencyconverter.data.api.CurrencyRepository
import com.brunofelixdev.currencyconverter.data.api.Resource
import com.brunofelixdev.currencyconverter.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<CurrencyState>(CurrencyState.Initial)
    val uiSateFlow: StateFlow<CurrencyState> get() = _uiStateFlow

    fun convert(request: CurrencyRequest) {
        if (request.amount == null) {
            _uiStateFlow.value = CurrencyState.Error("Not a valid amount.")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _uiStateFlow.value = CurrencyState.Loading

            when(val response = repository.fetchRates(request)) {
                is Resource.Error -> _uiStateFlow.value =
                    CurrencyState.Error(response.message ?: "Error response")
                is Resource.Success -> {
                    val result = response.data
                    if (result == null) {
                        _uiStateFlow.value = CurrencyState.Error("Unexpected error")
                    } else {
                        _uiStateFlow.value = CurrencyState.Success(result)
                    }
                }
            }
        }
    }
}