package ru.mozgolom112.weatherapp.ui.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.database.CityDao
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.repository.city.CityRepository
import ru.mozgolom112.weatherapp.repository.weatherapi.WeatherApiProviderInterface
import ru.mozgolom112.weatherapp.utils.asDomainModel
import ru.mozgolom112.weatherapp.utils.consts.DEFAULT_CITY
import ru.mozgolom112.weatherapp.utils.extensions.getTodayWeather
import ru.mozgolom112.weatherapp.utils.extensions.getTomorrowWeather

class WeatherDetailsViewModel(
    private val cityRepository: CityRepository,
    private val weatherApiProvider: WeatherApiProviderInterface,
    val selectedCity: City = DEFAULT_CITY

) : ViewModel() {

    private var dailyWeathers: List<DailyWeather>? = null
    private val _selectedDayWeather = MutableLiveData<DailyWeather?>(null)
    val selectedDayWeather: LiveData<DailyWeather?>
        get() = _selectedDayWeather

    private val _navigateToWeeklyForecast = MutableLiveData<Boolean>(false)
    val navigateToWeeklyForecast: LiveData<Boolean>
        get() = _navigateToWeeklyForecast

    private val _navigateToSavedCities = MutableLiveData<Boolean>(false)
    val navigateToSavedCities: LiveData<Boolean>
        get() = _navigateToSavedCities

    init {
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

