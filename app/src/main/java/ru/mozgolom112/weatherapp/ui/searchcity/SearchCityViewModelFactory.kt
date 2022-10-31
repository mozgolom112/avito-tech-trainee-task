package ru.mozgolom112.weatherapp.ui.searchcity

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.ui.savedcities.SavedCitiesViewModel

class SearchCityViewModelFactory(
    private val geocoder: Geocoder
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            return SearchCityViewModel(geocoder) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}