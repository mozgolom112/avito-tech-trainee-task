package ru.mozgolom112.weatherapp.repository.location

import ru.mozgolom112.weatherapp.domain.LocationData

interface LocationProviderInterface {
    fun getUserCurrentLocation(callback:RequestCompleteListener<LocationData>)
}