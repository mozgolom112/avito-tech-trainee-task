package ru.mozgolom112.weatherapp.repository.weatherapi

import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.network.WeatherApi
import ru.mozgolom112.weatherapp.utils.asDomainModel

class WeatherApiProvider(
    private val weatherApi: WeatherApi = RetrofitClient.weatherApi
) : WeatherApiProviderInterface {
    override suspend fun getWeatherTwoDaysForecast(
        latitude: Double,
        longitude: Double
    ): List<DailyWeather> {
        val response = weatherApi.getWeatherByLocation(
            latitude.toString(),
            longitude.toString(),
            TWO_DAY_FORECAST_LIMIT
        )
        return response.asDomainModel()
    }

    override suspend fun getWeeklyWeatherForecast(latitude: Double, longitude: Double): List<DailyWeather> {
        val response = weatherApi.getWeatherByLocation(
            latitude.toString(),
            longitude.toString(),
            WEEKLY_FORECAST_LIMIT
        )
        return response.asDomainModel()
    }

    private companion object{
        const val TWO_DAY_FORECAST_LIMIT = 2
        const val WEEKLY_FORECAST_LIMIT = 7
    }
}