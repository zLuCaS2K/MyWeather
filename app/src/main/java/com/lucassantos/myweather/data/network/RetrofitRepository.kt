package com.lucassantos.myweather.data.network

/**
 * PT-BR: Essa é a classe responsável pelo padrão de repositórios da camadas de serviços.
 * EN: This is a class responsible for the service layer repositories pattern.
 */

class RetrofitRepository constructor(private val retrofitService: RetrofitService) {

    fun getWeather(lat: String, lon: String, lang: String, units: String) =
        retrofitService.getResultWeather(lat, lon, lang, units)

    companion object {
        fun create(retrofitService: RetrofitService): RetrofitRepository {
            return RetrofitRepository(retrofitService)
        }
    }
}