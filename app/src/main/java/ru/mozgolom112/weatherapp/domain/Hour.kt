package ru.mozgolom112.weatherapp.domain

data class Hour(
    val time: String,
    val hour: String,
    val temp: Int?,
    val icon: String,
    val condition: String
)