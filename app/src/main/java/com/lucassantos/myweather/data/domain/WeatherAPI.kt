package com.lucassantos.myweather.data.domain

import com.google.gson.annotations.SerializedName

data class WeatherAPI(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)