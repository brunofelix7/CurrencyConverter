package com.brunofelixdev.currencyconverter.extension

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DoubleExtensionTest {

    @Test
    fun `test is the double value rounded to two decimal places`() {
        val myDouble = 3.144678

        val result = myDouble.round(2)

        assertThat(result).isEqualTo(3.14)
    }
}