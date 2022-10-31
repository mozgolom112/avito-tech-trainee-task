package ru.mozgolom112.weatherapp.ui.searchcity

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.asDomainModel

class SearchCityViewModel(private val geocoder: Geocoder) : ViewModel() {
    private val _availableCities = MutableLiveData<List<City?>>(null)
    val availableCities: LiveData<List<City?>>
        get() = _availableCities

    private val _navigateToWeatherDetailsWithCity = MutableLiveData<City?>(null)
    val navigateToWeatherDetailsWithCity: LiveData<City?>
        get() = _navigateToWeatherDetailsWithCity

    fun navigateToWeatherDetails(selectedCity: City) {
        _navigateToWeatherDetailsWithCity.value = selectedCity
    }

    fun updateAvailableCities(userInput: String) {
        if (userInput!=""){
            val res = geocoder.getFromLocationName(userInput, 10)
            _availableCities.value = res.asDomainModel()
        }
    }

    fun doneNavigating() {
        _navigateToWeatherDetailsWithCity.value = null
    }

}