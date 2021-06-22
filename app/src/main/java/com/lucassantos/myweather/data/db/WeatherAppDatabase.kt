package com.lucassantos.myweather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucassantos.myweather.model.Weather
import com.lucassantos.myweather.model.WeatherDAO

@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun getWeatherDAO(): WeatherDAO

    companion object {
        @Volatile
        private var INSTANCE: WeatherAppDatabase? = null

        fun getInstance(context: Context): WeatherAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context.applicationContext, WeatherAppDatabase::class.java, "MyWeather").build()
                INSTANCE = db
                db
            }
        }
    }
}