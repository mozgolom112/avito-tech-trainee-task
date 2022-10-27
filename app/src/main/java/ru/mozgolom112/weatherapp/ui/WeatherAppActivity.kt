package ru.mozgolom112.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mozgolom112.weatherapp.R

class WeatherAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_app)
    }
}