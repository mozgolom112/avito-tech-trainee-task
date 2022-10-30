package ru.mozgolom112.weatherapp.ui.weatherdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.utils.asDomainModel

class WeatherDetailsViewModel : ViewModel() {

    var dailyWeathers: List<DailyWeather>? = null
    private val _selectedDayWeather = MutableLiveData<DailyWeather?>(null)
    val selectedDayWeather: LiveData<DailyWeather?>
        get() = _selectedDayWeather

    init {
        viewModelScope.launch {
            val response = RetrofitClient.weatherApi.getWeatherByLocation("55.75396", "37.620393")
            dailyWeathers = response.asDomainModel()
            _selectedDayWeather.value = getTodayWeather()
        }
    }

    fun onTodayTabClick() {
        _selectedDayWeather.value = dailyWeathers?.get(0)
    }

    fun onTomorrowTabClick() {
        _selectedDayWeather.value = getTomorrowWeather()
    }

    fun onSevenDayForecastTabClick() {
        //_selectedDay.value = dailyWeathers?.get()
    }


    private fun getTodayWeather() = dailyWeathers?.get(0)
    private fun getTomorrowWeather() = dailyWeathers?.get(1)
}

