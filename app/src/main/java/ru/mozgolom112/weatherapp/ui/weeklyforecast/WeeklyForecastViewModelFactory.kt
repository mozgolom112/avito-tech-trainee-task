package ru.mozgolom112.weatherapp.ui.weeklyforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.repository.weatherapi.WeatherApiProvider

class WeeklyForecastViewModelFactory(
    private val selectedCity: City): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeeklyForecastViewModel::class.java)){
            return WeeklyForecastViewModel(selectedCity, WeatherApiProvider()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}