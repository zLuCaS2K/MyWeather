package com.lucassantos.myweather.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lucassantos.myweather.model.domain.WeatherAPI

class Converters {

    @TypeConverter
    fun listToJSON(value: List<WeatherAPI>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<WeatherAPI>::class.java).toList()

}