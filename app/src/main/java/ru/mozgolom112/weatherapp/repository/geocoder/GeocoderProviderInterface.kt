package ru.mozgolom112.weatherapp.repository.geocoder

import ru.mozgolom112.weatherapp.domain.City

interface GeocoderProviderInterface {
    fun getCitiesByLocationName(locationName: String): List<City>?
    fun getCityByCoordinates(latitude: Double, longitude: Double): City?
}