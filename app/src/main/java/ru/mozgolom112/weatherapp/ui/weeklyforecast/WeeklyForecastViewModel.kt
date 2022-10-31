package ru.mozgolom112.weatherapp.ui.weeklyforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.utils.asDomainModel

class WeeklyForecastViewModel(val city: City) : ViewModel() {
    private val _weeklyForecast = MutableLiveData<List<DailyWeather>?>(null)
    val weeklyForecast: LiveData<List<DailyWeather>?>
        get() = _weeklyForecast

    init {
        viewModelScope.launch {
            val response = RetrofitClient.weatherApi.getWeatherByLocation(
                city.lat.toString(),
                city.lon.toString(),
                limit = 7
            )
            _weeklyForecast.value = response.asDomainModel()
        }
    }
}