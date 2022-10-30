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





