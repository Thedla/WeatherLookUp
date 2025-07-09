package com.example.weatherlookup.data.service

import com.example.weatherlookup.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        // Need to handle differently in Production environment for simplicity i'm directly using the api key
        @Query("appid") apiKey: String = "65d00499677e59496ca2f318eb68c049"
    ): WeatherResponse
}