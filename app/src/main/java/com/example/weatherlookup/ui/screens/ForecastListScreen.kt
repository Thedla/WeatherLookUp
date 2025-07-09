package com.example.weatherlookup.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherlookup.data.model.ForecastItem
import com.example.weatherlookup.data.model.Resource
import com.example.weatherlookup.ui.AppSurface
import com.example.weatherlookup.ui.viewModel.WeatherViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ForecastDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun ForecastListScreen(
    navigator: DestinationsNavigator,
    city: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()

    LaunchedEffect(city) {
        viewModel.getForecast(city)
    }

    AppSurface(
        city = city,
        onCLick = { navigator.popBackStack() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (val state = weatherState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is Resource.Success -> {
                    val weatherData = state.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(weatherData.list) { index, forecastItem ->
                            ForecastRow(
                                forecastItem, onItemClick = {
                                    navigator.navigate(
                                        ForecastDetailScreenDestination(
                                            city,
                                            forecastItem
                                        )
                                    )
                                })
                        }
                    }
                }

                is Resource.Error -> {
                    Text("Error: ${state.message}", modifier = Modifier.align(Alignment.Center))
                    Button(
                        onClick = { viewModel.getForecast(city) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }

}


@Composable
fun ForecastRow(
    forecastItem: ForecastItem, onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (forecastItem.weather.isNotEmpty()) {
            Text(
                forecastItem.weather.first().main, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            forecastItem.main.temp.toString(), textAlign = TextAlign.Center
        )
    }
    HorizontalDivider(
        modifier = Modifier, thickness = DividerDefaults.Thickness, color = DividerDefaults.color
    )
}

