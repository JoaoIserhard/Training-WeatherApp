package com.example.weeatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.example.weeatherapp.data.DataOrException
import com.example.weeatherapp.model.Weather
import com.example.weeatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }
}