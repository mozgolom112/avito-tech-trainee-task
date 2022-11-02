package ru.mozgolom112.weatherapp.ui.weatherdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.repository.city.CityRepositoryProviderInterface
import ru.mozgolom112.weatherapp.repository.weatherapi.WeatherApiProviderInterface
import ru.mozgolom112.weatherapp.utils.consts.DEFAULT_CITY
import ru.mozgolom112.weatherapp.utils.extensions.getTodayWeather
import ru.mozgolom112.weatherapp.utils.extensions.getTomorrowWeather

class WeatherDetailsViewModel(
    private val cityRepository: CityRepositoryProviderInterface,
    private val weatherApiProvider: WeatherApiProviderInterface,
    var selectedCity: City = DEFAULT_CITY

) : ViewModel() {
    private var dailyWeathers: List<DailyWeather>? = null
    private val _selectedDayWeather = MutableLiveData<DailyWeather?>(null)
    val selectedDayWeather: LiveData<DailyWeather?>
        get() = _selectedDayWeather

    private val _navigateToWeeklyForecast = MutableLiveData<Boolean>(false)
    val navigateToWeeklyForecast: LiveData<Boolean>
        get() = _navigateToWeeklyForecast

    private val _favorite = MutableLiveData<Boolean?>(null)
    val favorite: LiveData<Boolean?>
        get() = _favorite

    private val _navigateToSavedCities = MutableLiveData<Boolean>(false)
    val navigateToSavedCities: LiveData<Boolean>
        get() = _navigateToSavedCities

    init {
        viewModelScope.launch {
            setCurrentCity()
            uploadForecast()
            _favorite.value =  withContext(Dispatchers.IO){cityRepository.isCitySaved(selectedCity)}  //выставляем кнопку правильно
        }
    }

    fun setCurrentCity() {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedCity == DEFAULT_CITY) {
                selectedCity = cityRepository.getCurrentCity() ?: DEFAULT_CITY
            }
            cityRepository.insertCityAsCurrent(selectedCity)
        }
    }

    fun uploadForecast() {
        viewModelScope.launch {
            try {
                dailyWeathers = withContext(Dispatchers.IO) {
                    weatherApiProvider.getWeeklyWeatherForecast(
                        selectedCity.lat,
                        selectedCity.lon
                    )
                }
                _selectedDayWeather.value = dailyWeathers.getTodayWeather()
            } catch (e: Exception) {
                //CheckInternetConnection or Other
            }
        }
    }

    fun btnFavorityClick() {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.insertCityAsOppositeSaved(selectedCity)
        }
        _favorite.value = _favorite.value?.not()
    }

    fun onTodayTabClick() {
        _selectedDayWeather.value = dailyWeathers.getTodayWeather()
    }

    fun onTomorrowTabClick() {
        _selectedDayWeather.value = dailyWeathers.getTomorrowWeather()
    }

    fun onSevenDayForecastTabClick() {
        _navigateToWeeklyForecast.value = true
    }

    fun onBtnNavigateToSavedCityClick() {
        _navigateToSavedCities.value = true
    }

    fun doneNavigating() {
        _navigateToWeeklyForecast.value = false
        _navigateToSavedCities.value = false
    }
}

