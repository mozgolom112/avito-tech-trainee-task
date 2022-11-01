package ru.mozgolom112.weatherapp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.utils.consts.LOCATION_PERMISSION_REQUEST_ID
import ru.mozgolom112.weatherapp.utils.extensions.checkPermissions
import ru.mozgolom112.weatherapp.utils.extensions.requestLocationPermissions
import ru.mozgolom112.weatherapp.utils.snackbars.showSnackBarWithMessage
import ru.mozgolom112.weatherapp.utils.snackbars.showSnackBarWithSettings

class WeatherAppActivity : AppCompatActivity() {
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_app)
        //проверка на разрешения
        if (!checkPermissions(this)) requestLocationPermissions(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showSnackBarWithMessage(findViewById(R.id.nav_host_fragment), "Использование геолакации разрешено")
            } else {
                showSnackBarWithSettings(
                    findViewById(R.id.nav_host_fragment),
                    "Геолакация недоступна. Измените настройки приложения."
                ) { startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            }
        }
    }
}