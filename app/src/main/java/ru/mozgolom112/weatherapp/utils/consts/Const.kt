package ru.mozgolom112.weatherapp.utils.consts

import ru.mozgolom112.weatherapp.domain.City

enum class WeatherDetailTypes(val type: String){
    WIND_SPEED("Скорость ветра"),
    HUMIDITY("Влажность воздуха"),
    PRESSURE("Давление"),
    PREC("Тип осадков"),
    SUNRISE("Восход"),
    SUNSET("Закат")
}

val DEFAULT_CITY = (City(55.75396, 37.620393, "Москва", "Россия"))

val TEMP_LIST_OF_CITIES = listOf(
    DEFAULT_CITY,
    (City(51.509865, -0.118092, "London", "UK")),
    (City(53.123, 42.142, "Ryzan", "Ru")),
    (City(53.541, 42.13, "Moscow", "Ru")),
    (City(53.56, 42.563, "Moscow", "Ru"))
)

