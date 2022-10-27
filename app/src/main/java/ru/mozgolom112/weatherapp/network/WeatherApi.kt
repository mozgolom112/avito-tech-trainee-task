package ru.mozgolom112.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.mozgolom112.weatherapp.network.dto.NetworkWeather

interface WeatherApi {
    @GET("forecast")
    suspend fun getWeatherByLocation(
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String,
        @Query("limit")
        limit: Int = 7
    ): NetworkWeather
}