package com.example.weeatherapp.repository

import android.util.Log
import com.example.weeatherapp.data.DataOrException
import com.example.weeatherapp.model.Weather
import com.example.weeatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi){
    suspend fun getWeather(cityQuery: String):DataOrException<Weather, Boolean, Exception>{
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception){

            Log.d("EX", "getWeather: ${e}")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}