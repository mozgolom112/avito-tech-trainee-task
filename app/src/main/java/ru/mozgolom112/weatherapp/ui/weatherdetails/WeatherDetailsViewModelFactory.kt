package ru.mozgolom112.weatherapp.ui.weatherdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.ui.savedcities.SavedCitiesViewModel
import ru.mozgolom112.weatherapp.utils.DEFAULT_CITY

class WeatherDetailsViewModelFactory(
    private val selectedCity: City?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java)) {
            return if (selectedCity != null){
                WeatherDetailsViewModel(selectedCity) as T
            } else{
                WeatherDetailsViewModel(DEFAULT_CITY) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}