package com.example.weatherlookup.di


import com.example.weatherlookup.data.repository.WeatherRepository
import com.example.weatherlookup.data.service.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService) = WeatherRepository(api)
}