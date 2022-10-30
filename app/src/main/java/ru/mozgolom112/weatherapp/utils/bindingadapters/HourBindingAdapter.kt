package ru.mozgolom112.weatherapp.utils.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.mozgolom112.weatherapp.R

@BindingAdapter("setHourTemp")
fun setHourTemp(textView: TextView, temp: Int) {
    textView.apply {
        text = resources.getString(R.string.degree_celsius_with_value, temp)
    }
}

@BindingAdapter("setHour")
fun setHour(textView: TextView, hour: String) {
    textView.text = when (hour) {
        "0" -> "00:00"
        "1" -> "01:00"
        "2" -> "02:00"
        "3" -> "03:00"
        "4" -> "04:00"
        "5" -> "05:00"
        "6" -> "06:00"
        "7" -> "07:00"
        "8" -> "08:00"
        "9" -> "09:00"
        "10" -> "11:00"
        "11" -> "11:00"
        "12" -> "12:00"
        "13" -> "13:00"
        "14" -> "14:00"
        "15" -> "15:00"
        "16" -> "16:00"
        "17" -> "17:00"
        "18" -> "18:00"
        "19" -> "19:00"
        "20" -> "20:00"
        "21" -> "21:00"
        "22" -> "22:00"
        "23" -> "23:00"
        "24" -> "24:00"
        else -> ""
    }
}