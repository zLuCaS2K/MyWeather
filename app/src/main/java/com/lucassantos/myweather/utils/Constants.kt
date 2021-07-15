package com.lucassantos.myweather.utils

/**
 * PT-BR: Essa classe tem como função fornecer contantes para serem usadas no projeto.
 * EN: This class has the function of providing constants to be used in the project.
 */

object Constants {

    object API {
        const val API_KEY = "54f4974233d0f3f0e5c05c3486b2596b"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val URL_ICON = "https://openweathermap.org/img/wn/"
    }

    object DIALOGS {
        const val DATA_LANGUAGE = "DataLanguage"
        const val LANGUAGE_APP = "LanguageApp"
        const val TEMPERATURE_UNIT = "TemperatureUnit"
    }

    object PREFERENCES {
        const val NAME = "WeatherSettings"
        const val NO_PREFERENCES = "noSettings"
        const val LANGUAGE_DATA = "language_data"
        const val LANGUAGE_APP = "language_app"
        const val TEMPERATURE_UNIT = "temperature_unit"
    }

    object REQUESTS {
        const val REQUEST_CODE_LOCATION = 150
    }
}