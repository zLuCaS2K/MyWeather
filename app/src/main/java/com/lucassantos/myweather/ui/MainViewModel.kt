package com.lucassantos.myweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucassantos.myweather.data.db.WeatherAppDatabase
import com.lucassantos.myweather.data.domain.ResultWeatherAPI
import com.lucassantos.myweather.data.network.RetrofitRepository
import com.lucassantos.myweather.data.network.RetrofitService
import com.lucassantos.myweather.model.Weather
import com.lucassantos.myweather.model.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherRepository: WeatherRepository
    private val _retrofitRepository: RetrofitRepository
    var mWeather = MutableLiveData<ResultWeatherAPI>()
    var isViewLoading = MutableLiveData<Boolean>()
    var anErrorOccurred = MutableLiveData<Boolean>()

    init {
        val weatherDB = WeatherAppDatabase.getInstance(application)
        _weatherRepository = WeatherRepository.create(weatherDB.getWeatherDAO())
        _retrofitRepository = RetrofitRepository.create(RetrofitService.getInstance())
    }

    fun insertWeather(weather: Weather) {
        viewModelScope.launch { _weatherRepository.insertWeather(weather) }
    }

    fun getWeather(lat: String, log: String) {
        viewModelScope.launch {
            isViewLoading.postValue(true)
            val response = _retrofitRepository.getWeather(lat, log)
            response.enqueue(object : Callback<ResultWeatherAPI>{
                override fun onResponse(
                    call: Call<ResultWeatherAPI>,
                    response: Response<ResultWeatherAPI>
                ) {
                    response.let {
                        isViewLoading.postValue(false)
                        mWeather.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResultWeatherAPI>, t: Throwable) {
                    t.printStackTrace()
                }

            })

        }
    }
}