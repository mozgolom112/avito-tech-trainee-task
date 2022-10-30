package ru.mozgolom112.weatherapp.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.mozgolom112.weatherapp.R

@BindingAdapter("setFeelsLike")
fun setFeelsLikeTemperature(textView: TextView, value: Int?) {
    textView.apply {
        if (value != null) {
            text = resources.getString(R.string.feels_like_ru, value)
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }
}



