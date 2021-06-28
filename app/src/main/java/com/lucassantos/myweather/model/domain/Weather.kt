package com.lucassantos.myweather.model.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Weather(

    @PrimaryKey(autoGenerate = true)
    val idWeather: Long = 0,

    @SerializedName("name")
    val location: String,

    @SerializedName("weather")
    val weatherAPI: List<WeatherAPI>,

    @SerializedName("main")
    @Embedded val main: Main,

    @SerializedName("wind")
    @Embedded val wind: Wind
)