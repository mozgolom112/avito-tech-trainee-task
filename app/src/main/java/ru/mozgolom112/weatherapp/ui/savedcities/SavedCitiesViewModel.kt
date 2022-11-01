package ru.mozgolom112.weatherapp.ui.savedcities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.consts.TEMP_LIST_OF_CITIES

class SavedCitiesViewModel(
    val currentCity: City?
) : ViewModel() {

    private val _savedCities = MutableLiveData<List<City>?>(null)
    val savedCities: LiveData<List<City>?>
        get() = _savedCities

    private val _navigateToWeatherDetailsWithCity = MutableLiveData<City?>(null)
    val navigateToWeatherDetailsWithCity: LiveData<City?>
        get() = _navigateToWeatherDetailsWithCity

    private val _isNavigatedToSearchCity = MutableLiveData<Boolean>(false)
    val isNavigatedToSearchCity: LiveData<Boolean>
        get() = _isNavigatedToSearchCity

    init {

        _savedCities.value = TEMP_LIST_OF_CITIES
    }


    fun onRemoveSavedCityClick(city: City) {
        Log.i("SavedCitiesViewModel", "onRemoveSavedCityClick $city")
    }

    fun navigateToWeatherDetails(city: City) {
        Log.i("SavedCitiesViewModel", "navigateToWeatherDetails $city")
        _navigateToWeatherDetailsWithCity.value = city
    }

    fun navigateToSearchCity() {
        _isNavigatedToSearchCity.value = true
    }

    fun doneNavigating(){
        _navigateToWeatherDetailsWithCity.value = null
        _isNavigatedToSearchCity.value = false
    }
}