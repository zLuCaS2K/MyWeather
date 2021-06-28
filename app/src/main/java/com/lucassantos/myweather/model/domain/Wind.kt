package com.lucassantos.myweather.model.domain

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("wind")
    val wind: Int
)