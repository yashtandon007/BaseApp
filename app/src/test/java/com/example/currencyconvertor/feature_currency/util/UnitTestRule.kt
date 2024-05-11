package com.example.currencyconvertor.feature_currency.util

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class UnitTestRule : TestWatcher() {
    override fun starting(description: Description) {
        isUnitTest  = true
    }

    override fun finished(description: Description) {
        isUnitTest  = false
    }
}
