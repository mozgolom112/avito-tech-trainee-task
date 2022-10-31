package ru.mozgolom112.weatherapp.utils.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.mozgolom112.weatherapp.R

@BindingAdapter("setCelsiusTemp")
fun setCelsiusTemp(textView: TextView, temp: Int) {
    textView.apply {
        text = resources.getString(R.string.degree_celsius_with_value, temp)
    }
}

@BindingAdapter("setFeelsLike")
fun setFeelsLikeCelsiusTemp(textView: TextView, value: Int?) {
    textView.apply {
        if (value != null) {
            text = resources.getString(R.string.feels_like_ru, value)
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }
}