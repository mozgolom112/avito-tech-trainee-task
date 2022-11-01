package ru.mozgolom112.weatherapp.ui.weeklyforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.repository.weatherapi.WeatherApiProviderInterface
import java.io.IOException

class WeeklyForecastViewModel(
    val selectedCity: City,
    private val weatherApiProvider: WeatherApiProviderInterface
) :
    ViewModel() {
    private val _weeklyForecast = MutableLiveData<List<DailyWeather>?>(null)
    val weeklyForecast: LiveData<List<DailyWeather>?>
        get() = _weeklyForecast

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    init {
        viewModelScope.launch {
            try {
                _weeklyForecast.value = withContext(Dispatchers.IO) {
                    weatherApiProvider.getWeeklyWeatherForecast(
                        selectedCity.lat,
                        selectedCity.lon
                    )
                }
            } catch (e: Exception) {
                //CheckInternetConnection or Other
                errorHandler(e)
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

    private fun updateErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun showErrorMessage() {
        updateErrorMessage(null)
    }
}