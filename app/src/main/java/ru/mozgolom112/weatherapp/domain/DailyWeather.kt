package ru.mozgolom112.weatherapp.domain

data class DailyWeather(
    val city: City,
    val date: String,       //дата для которой составлен прогноз
    val updateTime: String, //время загрузки прогноза
    val iconUrl: String,
    val temp: Int?,
    val tempFeelsLike: Int?,
    val condition: String?,
    val weatherParameters: List<WeatherParameter>?,
    val hours: List<Hour>?
)

data class City(
    val lat: Double,
    val lon: Double,
    val cityName: String? = null,
    val country: String? = null
)

data class WeatherParameter(
    val parameter: String,
    val value: String,
    val drawableIcon: Int
)

data class Hour(
    val time: String,
    val hour:String,
    val temp: Int?,
    val icon: String,
    val condition: String
)