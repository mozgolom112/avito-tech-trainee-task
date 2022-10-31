package ru.mozgolom112.weatherapp.ui.savedcities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.ui.weeklyforecast.WeeklyForecastViewModel

class SavedCitiesViewModelFactory(
    private val currentCity: City?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedCitiesViewModel::class.java)) {
            return SavedCitiesViewModel(currentCity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}