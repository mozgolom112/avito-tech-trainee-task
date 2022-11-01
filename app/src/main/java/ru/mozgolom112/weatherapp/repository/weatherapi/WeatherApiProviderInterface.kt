package ru.mozgolom112.weatherapp.repository.weatherapi

import ru.mozgolom112.weatherapp.domain.DailyWeather

interface WeatherApiProviderInterface {
    suspend fun getWeatherTwoDaysForecast(latitude: Double, longitude: Double): List<DailyWeather>
    suspend fun getWeeklyWeatherForecast(latitude: Double, longitude: Double): List<DailyWeather>
}