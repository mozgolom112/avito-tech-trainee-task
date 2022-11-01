package ru.mozgolom112.weatherapp.ui.searchcity

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.repository.geocoder.GeocoderProvider
import ru.mozgolom112.weatherapp.repository.location.LocationProvider
import java.util.*

class SearchCityViewModelFactory(
    private val context: Context,
    private val locale: Locale = Locale("RU")
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            val geocoderProvider = GeocoderProvider(context, locale)
            val locationProvider = LocationProvider(context)
            return SearchCityViewModel(geocoderProvider, locationProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}