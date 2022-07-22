package com.brunofelixdev.currencyconverter.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.brunofelixdev.currencyconverter.R
import com.brunofelixdev.currencyconverter.data.api.CurrencyRequest
import com.brunofelixdev.currencyconverter.databinding.ActivityCurrencyBinding
import com.brunofelixdev.currencyconverter.extension.hideKeyboard
import com.brunofelixdev.currencyconverter.extension.round
import com.brunofelixdev.currencyconverter.extension.shareResult
import com.brunofelixdev.currencyconverter.extension.toast
import com.brunofelixdev.currencyconverter.util.currencyFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyBinding
    private val viewModel: CurrencyViewModel by viewModels()
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiSetup()
        collectData()
    }

    private fun uiSetup() {
        setTheme(R.style.Theme_WCurrency)

        window.setBackgroundDrawableResource(R.drawable.bg_splash)

        binding = ActivityCurrencyBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        binding.textInputAmount.requestFocus()

        binding.btnConvert.setOnClickListener {
            val request = CurrencyRequest(
                binding.spinnerFrom.selectedItem.toString(),
                binding.spinnerTo.selectedItem.toString(),
                binding.textInputAmount.text.toString().toDouble()
            )
            viewModel.convert(request)
        }
    }

    private fun collectData() {
        uiStateJob = lifecycleScope.launch {
            viewModel.uiSateFlow.collect { uiState ->
                when(uiState) {
                    is CurrencyState.Loading -> {
                        binding.contentLayout?.isVisible = false
                        binding.progressBar.isVisible = true
                        hideKeyboard()
                        clearFields()
                    }
                    is CurrencyState.Success -> {
                        binding.contentLayout?.isVisible = true
                        binding.progressBar.isVisible = false

                        binding.textAmountFrom.text = currencyFormat(
                            uiState.data.query?.amount?.round(2),
                            uiState.data.query?.from
                        )
                        binding.textAmountTo.text = currencyFormat(
                            uiState.data.result?.round(2),
                            uiState.data.query?.to
                        )

                        binding.textSymbolFrom?.text = uiState.data.query?.from
                        binding.textSymbolTo?.text = uiState.data.query?.to

                        binding.textDate?.text = uiState.data.date

                        binding.btnShare?.setOnClickListener {
                            shareResult(uiState.data)
                        }
                    }
                    is CurrencyState.Error -> {
                        binding.contentLayout?.isVisible = false
                        binding.progressBar.isVisible = false
                        clearFields()
                        toast(uiState.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun clearFields() {
        binding.textAmountFrom.text = ""
        binding.textAmountTo.text = ""
        binding.textSymbolFrom?.text = ""
        binding.textSymbolTo?.text = ""
    }

    override fun onDestroy() {
        uiStateJob?.cancel()
        super.onDestroy()
    }
}
