package com.lucassantos.myweather.data.network

import com.lucassantos.myweather.model.domain.Weather
import com.lucassantos.myweather.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * PT-BR: RetrofitService usado para chamadas a API e para obter a instância do retrofit.
 * EN: RetrofitService used for API calls and to get a retrofit instance.
 */

interface RetrofitService {

    @GET("weather?&&&units=metric&appid=${Constants.API.API_KEY}&lang=pt_br")
    fun getResultWeather(
        @Query("lat") lat: String,
        @Query("lon") log: String
    ): Call<Weather>

    companion object {
        @Volatile
        private var INSTANCE: RetrofitService? = null

        fun getInstance(): RetrofitService {
            return INSTANCE ?: synchronized(this) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(RetrofitService::class.java)
                INSTANCE = retrofit
                retrofit
            }
        }
    }
}