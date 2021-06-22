package com.lucassantos.myweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lucassantos.myweather.data.db.WeatherAppDatabase
import com.lucassantos.myweather.model.Weather
import com.lucassantos.myweather.model.WeatherRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherRepository: WeatherRepository
    var mWeather: LiveData<Weather>

    init {
        val weatherDB = WeatherAppDatabase.getInstance(application)
        _weatherRepository = WeatherRepository.create(weatherDB.getWeatherDAO())
        mWeather = _weatherRepository.getWeather()
    }

    fun insertWeather(weather: Weather) {
        viewModelScope.launch { _weatherRepository.insertWeather(weather) }
    }
}