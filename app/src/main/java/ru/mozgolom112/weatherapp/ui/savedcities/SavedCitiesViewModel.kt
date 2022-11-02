package ru.mozgolom112.weatherapp.ui.savedcities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.repository.city.CityRepositoryProviderInterface
import ru.mozgolom112.weatherapp.utils.consts.TEMP_LIST_OF_CITIES

class SavedCitiesViewModel(
    private val cityRepository: CityRepositoryProviderInterface,
    val currentCity: City?
) : ViewModel() {
    val savedCities by lazy(Dispatchers.IO) { cityRepository.getListOfSavedCity() }

    private val _navigateToWeatherDetailsWithCity = MutableLiveData<City?>(null)
    val navigateToWeatherDetailsWithCity: LiveData<City?>
        get() = _navigateToWeatherDetailsWithCity

    private val _isNavigatedToSearchCity = MutableLiveData<Boolean>(false)
    val isNavigatedToSearchCity: LiveData<Boolean>
        get() = _isNavigatedToSearchCity

    fun onRemoveSavedCityClick(city: City) {
        viewModelScope.launch(Dispatchers.IO) { cityRepository.removeSavedCity(city) }
    }


    fun navigateToWeatherDetails(city: City) {
        Log.i("SavedCitiesViewModel", "navigateToWeatherDetails $city")
        _navigateToWeatherDetailsWithCity.value = city
    }

    fun navigateToSearchCity() {
        _isNavigatedToSearchCity.value = true
    }

    fun doneNavigating() {
        _navigateToWeatherDetailsWithCity.value = null
        _isNavigatedToSearchCity.value = false
    }
}