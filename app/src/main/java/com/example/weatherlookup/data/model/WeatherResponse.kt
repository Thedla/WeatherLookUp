package com.example.weatherlookup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WeatherResponse(val list: List<ForecastItem>, val city: CityInfo) : Parcelable

@Parcelize
data class ForecastItem(
    val dt: Long,
    val main: MainInfo,
    val weather: List<WeatherInfo>,
    val dt_txt: String
): Parcelable

@Parcelize
data class MainInfo(val temp: Float, val feels_like: Float): Parcelable

@Parcelize
data class WeatherInfo(val main: String, val description: String, val icon: String): Parcelable

@Parcelize
data class CityInfo(val name: String, val country: String): Parcelable