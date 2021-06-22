package com.lucassantos.myweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey val idWeather: Int = 0,
    val location: String,
    val temperature: Int,
    val description: String,
    val main: String,
    val feels_like: Int,
    val humidity: String,
    val wind: Int,
    val pressure: Int
)
