package com.lucassantos.myweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucassantos.myweather.data.db.WeatherAppDatabase
import com.lucassantos.myweather.data.network.RetrofitRepository
import com.lucassantos.myweather.data.network.RetrofitService
import com.lucassantos.myweather.model.domain.Weather
import com.lucassantos.myweather.model.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherRepository: WeatherRepository
    private val _retrofitRepository: RetrofitRepository
    var mWeather: LiveData<Weather>
    var isViewLoading = MutableLiveData<Boolean>()
    var anErrorOccurred = MutableLiveData<Boolean>()

    init {
        val weatherDB = WeatherAppDatabase.getInstance(application)
        _weatherRepository = WeatherRepository.create(weatherDB.getWeatherDAO())
        _retrofitRepository = RetrofitRepository.create(RetrofitService.getInstance())
        mWeather = _weatherRepository.getWeather()
    }

    fun insertWeather(weather: Weather) {
        viewModelScope.launch {
            _weatherRepository.insertWeather(weather)
            mWeather = _weatherRepository.getWeather()
        }
    }

    fun getWeather(lat: String, log: String) {
        viewModelScope.launch {
            isViewLoading.postValue(true)
            val response = _retrofitRepository.getWeather(lat, log)
            response.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body()?.let {
                        isViewLoading.postValue(false)
                        anErrorOccurred.postValue(false)
                        insertWeather(it)
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    isViewLoading.postValue(false)
                    anErrorOccurred.postValue(true)
                    t.printStackTrace()
                }
            })
        }
    }
}