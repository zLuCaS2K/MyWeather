package com.lucassantos.myweather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucassantos.myweather.model.Weather
import com.lucassantos.myweather.model.WeatherDAO

@Database(entities = [Weather::class], exportSchema = false, version = 1)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun getWeatherDAO(): WeatherDAO

    companion object {
        @Volatile
        private var INSTANCE: WeatherAppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeatherAppDatabase::class.java,
            "weatherDB.db"
        ).build()
    }
}