package com.lucassantos.myweather.data.network

import com.lucassantos.myweather.model.domain.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("weather?&&appid=54f4974233d0f3f0e5c05c3486b2596b")
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
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(RetrofitService::class.java)
                INSTANCE = retrofit
                retrofit
            }
        }
    }
}