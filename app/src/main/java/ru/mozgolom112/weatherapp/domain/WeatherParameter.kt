package ru.mozgolom112.weatherapp.domain

data class WeatherParameter(
    val parameter: String,
    val value: String,
    val drawableIcon: Int
)