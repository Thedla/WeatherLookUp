package com.example.weatherlookup.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherlookup.data.model.ForecastItem
import com.example.weatherlookup.data.model.MainInfo
import com.example.weatherlookup.data.model.WeatherInfo
import com.example.weatherlookup.ui.AppSurface
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun ForecastDetailScreen(
    navigator: DestinationsNavigator,
    cityName: String,
    forecastItem: ForecastItem,
) {

    AppSurface(
        city = cityName,
        onCLick = { navigator.popBackStack() }
    ) {
        DetailsCard(forecastItem)
    }
}

@Composable
fun DetailsCard(forecastItem: ForecastItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${forecastItem.main.temp.toInt()}",
                fontSize = 48.sp,
                fontWeight = Bold,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 20.dp),
                text = "Feels Like: ${forecastItem.main.feels_like.toInt()}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.End,
            )
        }

        Spacer(Modifier.height(16.dp))

        val weatherDesc = forecastItem.weather.firstOrNull()
        if (weatherDesc != null) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = weatherDesc.main,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
            )
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = weatherDesc.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview(backgroundColor = 0xFFF5F5F5, showBackground = true)
@Composable
fun DetailCardPreview() {
    DetailsCard(
        forecastItem = ForecastItem(
            dt = 12345676543,
            main = MainInfo(temp = 123.00f, feels_like = 100.00f),
            weather = listOf(WeatherInfo("Clear", "Clear Sky", icon = "")),
            dt_txt = "2023-09-01 12:00:00"
        )
    )
}
