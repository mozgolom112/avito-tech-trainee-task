package ru.mozgolom112.weatherapp.utils.snackbars

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBarWithMessage(view: View, message: String) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .show()


fun showSnackBarWithSettings(view: View, message: String, settingIntentAction: () -> Unit ) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    .setAction("Настройки") {
        settingIntentAction()
    }
.show()