package com.example.weatherlookup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weatherlookup.ui.navigation.AppNavHost
import com.example.weatherlookup.ui.theme.WeatherLookUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherLookUpTheme {
                AppNavHost()
            }
        }
    }
}