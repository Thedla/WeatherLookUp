package com.example.weatherlookup.ui.viewModel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlookup.data.model.Resource
import com.example.weatherlookup.data.model.WeatherResponse
import com.example.weatherlookup.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading)
    val weatherState: StateFlow<Resource<WeatherResponse>> = _weatherState.asStateFlow()

    private var currentCity: String? = null


    init {
        savedStateHandle.get<Resource<WeatherResponse>>(WEATHER_STATE_KEY)?.let {
            _weatherState.value = it
        }
        savedStateHandle.get<String>(CURRENT_CITY_KEY)?.let {
            currentCity = it
        }
    }

    fun getForecast(city: String) {
        if (_weatherState.value is Resource.Loading && city == currentCity) {
            return
        }
        clearForecast()
        currentCity = city
        savedStateHandle[CURRENT_CITY_KEY] = city
        _weatherState.value = Resource.Loading

        viewModelScope.launch {
            _weatherState.value = repo.getForecast(city)
            val currentState = weatherState.value
            if(currentState is Resource.Success) {
                savedStateHandle[WEATHER_STATE_KEY] = currentState.data
            }
        }
    }

    fun clearForecast() {
        _weatherState.value = Resource.Loading
        currentCity = null
        savedStateHandle.remove<WeatherResponse>(WEATHER_STATE_KEY)
        savedStateHandle.remove<String>(CURRENT_CITY_KEY)
    }

    companion object {
        private const val WEATHER_STATE_KEY = "weatherStateKey"
        private const val CURRENT_CITY_KEY = "currentCityKey"
    }

}
