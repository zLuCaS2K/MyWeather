package com.lucassantos.myweather.data.domain

import com.google.gson.annotations.SerializedName

data class ResultWeatherAPI(
    @SerializedName("name")
    val location: String,
    @SerializedName("weather")
    val weatherAPI: List<WeatherAPI>,
    @SerializedName("main")
    val mainAPI: MainAPI,
    @SerializedName("wind")
    val windAPI: WindAPI
)