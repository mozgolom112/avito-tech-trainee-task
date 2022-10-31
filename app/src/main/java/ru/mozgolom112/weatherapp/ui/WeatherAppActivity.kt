package ru.mozgolom112.weatherapp.ui

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mozgolom112.weatherapp.R
import java.util.*

class WeatherAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val geocoder = Geocoder(applicationContext, Locale("RU")).getFromLocationName("Petergurg", 6)

        setContentView(R.layout.activity_weather_app)
    }
}