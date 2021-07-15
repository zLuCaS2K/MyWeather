package com.lucassantos.myweather.ui

import android.app.Application
import android.util.Log
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

    /**
     * PT-BR: Essa função salva os dados no banco de dados.
     * EN: This function saves the data to the database.
     */
    fun insertWeather(weather: Weather) {
        viewModelScope.launch {
            _weatherRepository.insertWeather(weather)
            mWeather = _weatherRepository.getWeather()
        }
    }

    /**
     * PT-BR: Fazendo o request a API e salvando os dados retirada no banco de dados.
     * EN: Requesting the API and saving the retrieved data in the database.
     */
    fun getWeather(lat: String, lon: String, lang: String, units: String) {
        viewModelScope.launch {
            isViewLoading.postValue(true) /** Loading start */
            val response = _retrofitRepository.getWeather(lat, lon, lang, units)
            response.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body()?.let {
                        isViewLoading.postValue(false) /** Loading end */
                        anErrorOccurred.postValue(false) /** Error false */
                        insertWeather(it)
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    isViewLoading.postValue(false) /** Loading end */
                    anErrorOccurred.postValue(true) /** Error true */
                    t.printStackTrace()
                }
            })
        }
    }
}