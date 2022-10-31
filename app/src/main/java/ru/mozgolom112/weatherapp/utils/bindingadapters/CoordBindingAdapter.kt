package ru.mozgolom112.weatherapp.utils.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.mozgolom112.weatherapp.R

@BindingAdapter("setLatRu")
fun setLatRu(textView: TextView, lat: Double) {
    textView.apply {
        text = resources.getString(R.string.lat_ru, lat)
    }
}

@BindingAdapter("setLonRu")
fun setLonRu(textView: TextView, lon: Double) {
    textView.apply {
        text = resources.getString(R.string.lon_ru, lon)
    }
}