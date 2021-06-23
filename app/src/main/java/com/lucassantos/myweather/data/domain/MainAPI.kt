package com.lucassantos.myweather.data.domain

import com.google.gson.annotations.SerializedName

data class MainAPI(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feels_like: Double,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("pressure")
    val pressure: Int,
)