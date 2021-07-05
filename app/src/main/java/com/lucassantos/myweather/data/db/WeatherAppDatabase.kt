package com.lucassantos.myweather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucassantos.myweather.model.dao.WeatherDAO
import com.lucassantos.myweather.model.domain.Weather
import com.lucassantos.myweather.utils.Converters

/**
 * PT-BR: WeatherAppDatabase com singleton.
 * EN: WeatherAppDatabase with singleton.
 */

@Database(entities = [Weather::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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