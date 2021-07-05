package com.lucassantos.myweather.model.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * PT-BR: Única entidade do banco de dados, será obtida pela camada de serviços e porteriormente salva no banco de dados.
 * EN: The only entity from the database, it will be obtained by the services layer and later saved in the database.
 */

@Entity
data class Weather(

    @PrimaryKey
    val idWeather: Long = 1,

    @SerializedName("name")
    val location: String,

    @SerializedName("weather")
    val weatherAPI: List<WeatherAPI>,

    @SerializedName("main")
    @Embedded val main: Main,

    @SerializedName("wind")
    @Embedded val wind: Wind
)