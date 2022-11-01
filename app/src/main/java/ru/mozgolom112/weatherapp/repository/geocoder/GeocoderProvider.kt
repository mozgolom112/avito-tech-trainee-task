package ru.mozgolom112.weatherapp.repository.geocoder

import android.content.Context
import android.location.Geocoder
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.asDomainModelWithLocalityNullCheck
import java.util.*

class GeocoderProvider(
    context: Context, locale: Locale = Locale("RU"),
    private val geocoder: Geocoder = Geocoder(context, locale)
) : GeocoderProviderInterface {

    override fun getCitiesByLocationName(locationName: String): List<City>? {
        val res = geocoder.getFromLocationName(locationName, LIMIT)
        return res.asDomainModelWithLocalityNullCheck()
    }

    override fun getCityByCoordinates(latitude: Double, longitude: Double): City? {
        val res = geocoder.getFromLocation(latitude, longitude, LIMIT_ONE_CITY)
        return res.asDomainModelWithLocalityNullCheck().getOrNull(0)
    }

    private companion object {
        const val LIMIT = 10
        const val LIMIT_ONE_CITY = 1
    }
}