package com.lucassantos.myweather.data.domain

import com.google.gson.annotations.SerializedName

data class WindAPI(
    @SerializedName("wind")
    val wind: Int
)