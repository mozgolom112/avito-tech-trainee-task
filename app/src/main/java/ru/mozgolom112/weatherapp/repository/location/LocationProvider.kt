package ru.mozgolom112.weatherapp.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import ru.mozgolom112.weatherapp.domain.LocationData

class LocationProvider(context: Context): LocationProviderInterface {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    override fun getUserCurrentLocation(callback: RequestCompleteListener<LocationData>) {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it.also { callback.onRequestCompleted(setLocationData(it)) }
        } .addOnFailureListener {
            callback.onRequestFailed(it.localizedMessage)
        }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private companion object{
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000
        ).apply {
            setMaxUpdates(1)
        }.build()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
                setLocationData(it)
            }
        }
    }

    private fun setLocationData(location: Location):LocationData {
        return LocationData(longitude = location.longitude, latitude = location.latitude)
    }
}