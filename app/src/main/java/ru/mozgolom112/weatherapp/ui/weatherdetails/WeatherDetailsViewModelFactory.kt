package ru.mozgolom112.weatherapp.ui.weatherdetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mozgolom112.weatherapp.database.CityDao
import ru.mozgolom112.weatherapp.database.CityDatabase
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.repository.city.CityRepositoryProvider
import ru.mozgolom112.weatherapp.repository.weatherapi.WeatherApiProvider

class WeatherDetailsViewModelFactory(
    private val context: Context,
    private val selectedCity: City?,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java)) {
            val cityRepositoryProvider = CityRepositoryProvider(context)
            return if (selectedCity != null) {
                WeatherDetailsViewModel(cityRepositoryProvider, WeatherApiProvider(), selectedCity) as T
            } else {
                //Взять либо дефолтный, либо сходить в бд
                WeatherDetailsViewModel(cityRepositoryProvider, WeatherApiProvider()) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}