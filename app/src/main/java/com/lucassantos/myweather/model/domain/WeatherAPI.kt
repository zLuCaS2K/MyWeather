package com.lucassantos.myweather.model.domain

import com.google.gson.annotations.SerializedName

/**
 * PT-BR: Classe usada para complementar a classe Weather.
 * EN: Class used to complement the Weather class.
 */

data class WeatherAPI(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)