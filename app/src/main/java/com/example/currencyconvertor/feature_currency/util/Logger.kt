package com.example.currencyconvertor.feature_currency.util

import android.util.Log
import com.example.currencyconvertor.BuildConfig.DEBUG
import com.example.currencyconvertor.feature_currency.util.Constants.TAG


var isUnitTest = false
/**
 *
 * Prints a log message with the class name as a tag.
 *
 * @param className The name of the class
 * @param message The message to be printed
 */
fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}

