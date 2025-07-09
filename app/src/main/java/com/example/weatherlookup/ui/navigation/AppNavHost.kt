package com.example.weatherlookup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController
    )
}