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
    private val _selectedDay = MutableLiveData<DailyWeather?>(null)
    val selectedDay: LiveData<DailyWeather?> //selectedDayWeather
        get() = _selectedDay

    init {
        viewModelScope.launch {
            var response = RetrofitClient.weatherApi.getWeatherByLocation("55.75396", "37.620393")
            dailyWeathers = response.asDomainModel()
            println(dailyWeathers?.get(0).toString())
            _selectedDay.value = dailyWeathers?.get(0)
        }

    }

    fun onTodayTabClick(){
        _selectedDay.value = dailyWeathers?.get(0)
    }
    fun onTomorrowTabClick(){
        _selectedDay.value = dailyWeathers?.get(1)
    }
    fun onSevenDayForecastTabClick(){
        //_selectedDay.value = dailyWeathers?.get()
    }
}

