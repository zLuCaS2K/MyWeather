package com.lucassantos.myweather.model.repository

import androidx.lifecycle.LiveData
import com.lucassantos.myweather.model.dao.WeatherDAO
import com.lucassantos.myweather.model.domain.Weather

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