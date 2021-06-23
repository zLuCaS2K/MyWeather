package com.lucassantos.myweather.data.network

class RetrofitRepository constructor(private val retrofitService: RetrofitService) {

    fun getWeather(lat: String, log: String) = retrofitService.getResultWeather(lat, log)

    companion object {
        fun create(retrofitService: RetrofitService): RetrofitRepository {
            return RetrofitRepository(retrofitService)
        }
    }
}