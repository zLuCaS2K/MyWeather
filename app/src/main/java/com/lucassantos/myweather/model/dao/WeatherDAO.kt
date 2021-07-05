package com.lucassantos.myweather.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucassantos.myweather.model.domain.Weather

/**
 * PT-BR: DAO para entidade Weather.
 * EN: DAO for entity Weather.
 */

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM weather")
    fun getWeather(): LiveData<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

}