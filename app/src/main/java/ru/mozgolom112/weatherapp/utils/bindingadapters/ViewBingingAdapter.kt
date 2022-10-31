package ru.mozgolom112.weatherapp.utils.bindingadapters

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, value: Any?){
    //Не надо вставлять очень большой текст, даже не смотря на ограничение в три строчки
    if (value == null) {view.visibility = View.GONE}
}