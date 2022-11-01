package ru.mozgolom112.weatherapp.ui.searchcity

import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.LocationData
import ru.mozgolom112.weatherapp.repository.location.LocationProvider
import ru.mozgolom112.weatherapp.repository.location.RequestCompleteListener
import ru.mozgolom112.weatherapp.utils.asDomainModelWithLocalityNullCheck
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class SearchCityViewModel(
    private val geocoder: Geocoder,
    private val locationProvider: LocationProvider
) : ViewModel() {
    private val _availableCities = MutableLiveData<List<City>?>(null)
    val availableCities: LiveData<List<City>?>
        get() = _availableCities

    private val _navigateToWeatherDetailsWithCity = MutableLiveData<City?>(null)
    val navigateToWeatherDetailsWithCity: LiveData<City?>
        get() = _navigateToWeatherDetailsWithCity

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun navigateToWeatherDetails(selectedCity: City) {
        _navigateToWeatherDetailsWithCity.value = selectedCity
    }

    fun updateAvailableCities(userInput: String) {
        if (userInput != "") {
            val res = geocoder.getFromLocationName(userInput, 10)
            _availableCities.value = res.asDomainModelWithLocalityNullCheck()
        }
    }

    fun showErrorMessage() {
        _errorMessage.value = null
    }

    fun doneNavigating() {
        _navigateToWeatherDetailsWithCity.value = null
    }

    fun getCurrentLocation() {
        locationProvider.getUserCurrentLocation(object : RequestCompleteListener<LocationData> {
            override fun onRequestCompleted(data: LocationData) {
                val foundCity = getCityByLocation(data)
                if (foundCity != null) {
                    _navigateToWeatherDetailsWithCity.value = foundCity
                } else {
                    _errorMessage.value =
                        "Город по вашей локации (${(data.latitude * 100.0).roundToInt() / 100.0}; ${(data.longitude * 100.0).roundToInt() / 100.0}) не найден. Пожалуйста, воспользуйтесь поиском"
                }
            }

            override fun onRequestFailed(errorMessage: String?) {
                _errorMessage.value = errorMessage
            }
        })
    }

    private fun getCityByLocation(data: LocationData): City? =
        geocoder.getFromLocation(data.latitude, data.longitude, 1)
            .asDomainModelWithLocalityNullCheck().getOrNull(0)

}