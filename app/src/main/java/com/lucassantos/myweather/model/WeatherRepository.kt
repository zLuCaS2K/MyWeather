package com.lucassantos.myweather.model

import androidx.lifecycle.LiveData

class WeatherRepository private constructor(private val _weatherDAO: WeatherDAO) {

    fun getWeather(): LiveData<Weather> = _weatherDAO.getWeather()

    suspend fun insertWeather(weather: Weather) {
        _weatherDAO.insertWeather(weather)
    }

    companion object {
        fun create(weatherDAO: WeatherDAO): WeatherRepository {
            return WeatherRepository(weatherDAO)
        }
    }
}