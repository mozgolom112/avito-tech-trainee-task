package ru.mozgolom112.weatherapp.ui.searchcity

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.repository.location.LocationProvider
import ru.mozgolom112.weatherapp.ui.savedcities.SavedCitiesViewModel

class SearchCityViewModelFactory(
    private val geocoder: Geocoder,
    private val locationProvider: LocationProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            return SearchCityViewModel(geocoder, locationProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}