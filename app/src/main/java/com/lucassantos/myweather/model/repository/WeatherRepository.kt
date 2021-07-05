package com.lucassantos.myweather.model.repository

import androidx.lifecycle.LiveData
import com.lucassantos.myweather.model.dao.WeatherDAO
import com.lucassantos.myweather.model.domain.Weather

/**
 * PT-BR: Essa é a classe responsável pelo padrão de repositórios da camada de dados locais.
 * EN: This is a class responsible for the local data tier repositories pattern.
 */

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