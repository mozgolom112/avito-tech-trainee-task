package ru.mozgolom112.weatherapp.utils.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import ru.mozgolom112.weatherapp.utils.consts.LOCATION_PERMISSION_REQUEST_ID

fun checkPermissions(context: Context): Boolean {
    if (
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || //позволяет использовать approximately location
        ActivityCompat.checkSelfPermission
            (
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        return true
    }
    return false
}

fun requestLocationPermissions(activity: Activity) {
    //extention
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        LOCATION_PERMISSION_REQUEST_ID
    )
}

fun isLocationEnabled(activity: Activity): Boolean {
    var locationManager: LocationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}