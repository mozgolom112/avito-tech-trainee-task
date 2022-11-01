package ru.mozgolom112.weatherapp.ui.searchcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.LocationData
import ru.mozgolom112.weatherapp.repository.geocoder.GeocoderProviderInterface
import ru.mozgolom112.weatherapp.repository.location.LocationProviderInterface
import ru.mozgolom112.weatherapp.repository.location.RequestCompleteListener
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt

class SearchCityViewModel(
    private val geocoderProvider: GeocoderProviderInterface,
    private val locationProvider: LocationProviderInterface
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
            viewModelScope.launch {
                try {
                    _availableCities.value = withContext(Dispatchers.IO) {
                        geocoderProvider.getCitiesByLocationName(userInput)
                    }
                } catch (e: Exception) {
                    errorHandler(e)
                }
            }
        }
    }

    private fun errorHandler(e: Exception) {
        val message = when (e) {
            is IOException -> "Нет интернет соединения"
            else -> e?.message ?: e.toString()
        }
        updateErrorMessage(message)
    }

    private fun getCityByLocation(data: LocationData): City? =
        try {
            geocoderProvider.getCityByCoordinates(data.latitude, data.longitude)
        } catch (e: Exception) {
            errorHandler(e)
            null
        }

    fun setCurrentLocation() {
        locationProvider.getUserCurrentLocation(requestCompleteListener())
    }

    private fun requestCompleteListener() = object : RequestCompleteListener<LocationData> {
        override fun onRequestCompleted(data: LocationData) {
            val foundCity = getCityByLocation(data)
            if (foundCity != null) {
                _navigateToWeatherDetailsWithCity.value = foundCity
            } else {
                updateErrorMessage(
                    """Город по вашей локации (${(data.latitude * 100.0).roundToInt() / 100.0};
                                | ${(data.longitude * 100.0).roundToInt() / 100.0}) не найден.
                                |  Пожалуйста, воспользуйтесь поиском""".trimMargin()
                )
            }
        }

        override fun onRequestFailed(errorMessage: String?) {
            _errorMessage.value = errorMessage
        }
    }

    private fun updateErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun showErrorMessage() {
        updateErrorMessage(null)
    }

    fun doneNavigating() {
        _navigateToWeatherDetailsWithCity.value = null
    }
}