package ru.mozgolom112.weatherapp.domain

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class City(
    val lat: Double,
    val lon: Double,
    val cityName: String? = null,
    val country: String? = null
) : Parcelable