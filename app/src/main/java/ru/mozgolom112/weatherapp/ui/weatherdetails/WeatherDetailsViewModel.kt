package ru.mozgolom112.weatherapp.ui.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.utils.asDomainModel
import ru.mozgolom112.weatherapp.utils.extensions.getTodayWeather
import ru.mozgolom112.weatherapp.utils.extensions.getTomorrowWeather

class WeatherDetailsViewModel : ViewModel() {

    val city: City = City(55.75396, 37.620393, "Moscow", "Russia")

    var dailyWeathers: List<DailyWeather>? = null
    private val _selectedDayWeather = MutableLiveData<DailyWeather?>(null)
    val selectedDayWeather: LiveData<DailyWeather?>
        get() = _selectedDayWeather

    private val _navigateToWeeklyForecast = MutableLiveData<Boolean>(false)
    val navigateToWeeklyForecast: LiveData<Boolean>
        get() = _navigateToWeeklyForecast

    init {
        viewModelScope.launch {
            val response = RetrofitClient.weatherApi.getWeatherByLocation(city.lat.toString(), city.lon.toString())
            dailyWeathers = response.asDomainModel()
            _selectedDayWeather.value = dailyWeathers.getTodayWeather()
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

    fun doneNavigating(){
        _navigateToWeeklyForecast.value = false
    }


}

