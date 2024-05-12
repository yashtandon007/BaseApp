package com.example.currencyconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconvertor.feature_currency.presentation.CurrencyEvent
import com.example.currencyconvertor.feature_currency.presentation.CurrencyViewModel
import com.example.currencyconvertor.feature_currency.presentation.NotesScreen
import com.example.currencyconvertor.feature_currency.presentation.util.Screen
import com.example.currencyconvertor.feature_currency.util.printLogD
import com.example.currencyconvertor.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CurrencyScreen.route
                    ) {
                        composable(Screen.CurrencyScreen.route) {
                            val vm = hiltViewModel<CurrencyViewModel>()
                            NotesScreen(vm.state) { event ->
                                printLogD("mainActivity", " onEvent, : $event")
                                vm.onEvent(event)
                            }
                        }
                    }
                }
            }
        }
    }
}





