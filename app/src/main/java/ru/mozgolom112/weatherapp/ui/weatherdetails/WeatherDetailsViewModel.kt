package ru.mozgolom112.weatherapp.ui.weatherdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import ru.mozgolom112.weatherapp.network.RetrofitClient
import ru.mozgolom112.weatherapp.network.dto.NetworkWeather
import ru.mozgolom112.weatherapp.utils.APP_ID

class WeatherDetailsViewModel : ViewModel() {

    var networkWeather: NetworkWeather? = null

    init {
        viewModelScope.launch {
            var res = RetrofitClient.weatherApi.getWeatherByLocation("33.44", "-94.04", 1)

//            var networkWeather2 = withContext(Dispatchers.IO) {
//                RetrofitClient.api.getWeatherByLocation("33.44", "-94.04")
//            }
            Log.i("NetworkTestInScope", "${res.toString()}")
        }

    }
}

