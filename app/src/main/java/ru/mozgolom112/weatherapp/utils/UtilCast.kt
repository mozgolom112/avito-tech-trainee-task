package ru.mozgolom112.weatherapp.utils

import android.location.Address
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.domain.Hour
import ru.mozgolom112.weatherapp.domain.WeatherParameter
import ru.mozgolom112.weatherapp.network.dto.NetworkForecast
import ru.mozgolom112.weatherapp.network.dto.NetworkHour
import ru.mozgolom112.weatherapp.network.dto.NetworkWeather
import ru.mozgolom112.weatherapp.network.dto.NetworkWeatherDetails
import ru.mozgolom112.weatherapp.utils.WeatherDetailTypes.*

fun NetworkWeather.asDomainModel(): List<DailyWeather> {

    val list = mutableListOf<DailyWeather>()

    val city = City(
        this.city_info.lat,
        this.city_info.lon,
        this.city_info.tzifno.name?.substringAfter('/'),
        this.city_info.tzifno.name?.substringBefore('/')
    )

    list.addAll(this.forecasts.map {
        DailyWeather(
            city = city,
            date = it.date,
            updateTime = this.now_dt,
            iconUrl = it.parts.day_short.icon,
            temp = it.parts.day_short.temp ?: null,
            tempFeelsLike = it.parts.day_short.feels_like,
            condition = it.parts.day_short.condition,
            weatherParameters = it.parts.day_short.getWeatherParameters() + it.getSunRiseAndSetTimeAsParameters(),
            hours = it.hours?.getDomainHours()
        )
    })
    //Заменяем сегодняшний прогноз, на текущий если необходимо
    val now_forecast =
        DailyWeather(
            city = city,
            date = this.forecasts[0].date,
            updateTime = this.now_dt,
            iconUrl = this.forecast_now.icon,
            temp = this.forecast_now.temp ?: null,
            tempFeelsLike = this.forecast_now.feels_like,
            condition = this.forecast_now.condition,
            weatherParameters = this.forecast_now.getWeatherParameters() + this.forecasts[0].getSunRiseAndSetTimeAsParameters(),
            hours = this.forecasts[0].hours?.getDomainHours()
        )
    list[0] = now_forecast
    return list
}

fun List<NetworkHour>.getDomainHours(): List<Hour> =
    this.map {
        Hour(
            time = it.hour_ts,
            hour = it.hour,
            temp = it.temp,
            icon = it.icon,
            condition = it.condition
        )
    }

fun NetworkForecast.getSunRiseAndSetTimeAsParameters(): List<WeatherParameter> {
    val list: MutableList<WeatherParameter> = mutableListOf()
    if (this.sunset != null) {
        list.add(WeatherParameter(SUNSET.type, this.sunset, getIconDrawableRes(SUNSET)))
    }
    if (this.sunrise != null) {
        list.add(WeatherParameter(SUNRISE.type, this.sunrise, getIconDrawableRes(SUNRISE)))
    }
    return list
}

fun NetworkWeatherDetails.getWeatherParameters(): List<WeatherParameter> {
    val list: MutableList<WeatherParameter> = mutableListOf()
    if (this.wind_speed != null) {
        list.add(
            WeatherParameter(
                WIND_SPEED.type, "$wind_speed м/c", getIconDrawableRes(WIND_SPEED)
            )
        )
    }
    if (this.humidity != null) {
        list.add(
            WeatherParameter(
                HUMIDITY.type, "$humidity%", getIconDrawableRes(HUMIDITY)
            )
        )
    }
    if (this.pressure_mm != null) {
        list.add(
            WeatherParameter(
                PRESSURE.type, "$pressure_mm мм рт. ст.", getIconDrawableRes(PRESSURE)
            )
        )
    }
    if (this.prec_type != null) {
        val value = when (this.prec_type) {
            0 -> "без осадков"
            1 -> "дождь"
            2 -> "дождь со снегом"
            3 -> "снег"
            else -> ""
        }
        list.add(
            WeatherParameter(PREC.type, "$value", getIconDrawableRes(PREC))
        )
    }
    return list
}

fun getIconDrawableRes(type: WeatherDetailTypes): Int = when (type) {
    WIND_SPEED -> R.drawable.wind_weather_icon
    HUMIDITY -> R.drawable.humidity_icon
    PRESSURE -> R.drawable.pressure_air_icon
    PREC -> R.drawable.prec_icon
    SUNRISE -> R.drawable.sunrise_icon
    SUNSET -> R.drawable.sunset_icon
}

fun List<Address>.asDomainModelWithLocalityNullCheck(): List<City>  {
    val list = mutableListOf<City>()
    for (addr in this){
        if (addr.locality != null){
         list.add(City(
             lat = addr.latitude,
             lon = addr.longitude,
             cityName = addr.locality,
             country = addr.countryName
         ))
        }
    }
    return list
}

fun List<Address>.asDomainModel(): List<City> = map{
    City(
        lat = it.latitude,
        lon = it.longitude,
        cityName = it.locality,
        country = it.countryName
    )
}
