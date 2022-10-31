package ru.mozgolom112.weatherapp.utils.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.domain.WeatherParameter

fun List<DailyWeather>?.getTodayWeather() = this?.get(0)
fun List<DailyWeather>?.getTomorrowWeather() = this?.get(1)

fun List<WeatherParameter>?.getFirst() = this?.get(0)
fun List<WeatherParameter>?.getSecond() = this?.get(1)
fun List<WeatherParameter>?.getThird() = this?.get(2)

fun View.setGoneOrVisible(isGone: Boolean) {
    visibility = if (isGone) GONE else VISIBLE
}
