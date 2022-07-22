package com.brunofelixdev.currencyconverter.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.brunofelixdev.currencyconverter.R
import com.brunofelixdev.currencyconverter.data.api.CurrencyDto
import com.brunofelixdev.currencyconverter.util.currencyFormat

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.shareResult(obj: CurrencyDto) {
    val builder = StringBuilder().apply {
        append(getString(R.string.app_name).uppercase())
        append("\nDATE: ${obj.date}")
        append("\n\n")
        append("From: ${currencyFormat(obj.query?.amount, obj.query?.from)} (${obj.query?.from})")
        append("\n")
        append("To: ${currencyFormat(obj.result, obj.query?.to)} (${obj.query?.to})")
    }

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, builder.toString())
        type = "text/plain"
    }

    startActivity(Intent.createChooser(intent, null))
}