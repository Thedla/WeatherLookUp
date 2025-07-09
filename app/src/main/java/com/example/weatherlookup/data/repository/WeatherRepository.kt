package com.example.weatherlookup.data.repository

import com.example.weatherlookup.data.model.Resource
import com.example.weatherlookup.data.model.WeatherResponse
import com.example.weatherlookup.data.service.WeatherApiService
import com.example.weatherlookup.data.service.safeApiCall
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApiService) {
    suspend fun getForecast(city: String): Resource<WeatherResponse> {
        return safeApiCall {
            api.getForecast(city)
        }
    }
}