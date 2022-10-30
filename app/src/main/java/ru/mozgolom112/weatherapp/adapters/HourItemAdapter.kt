package ru.mozgolom112.weatherapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.mozgolom112.weatherapp.adapters.diffcallbacks.HourItemDiffCallback
import ru.mozgolom112.weatherapp.domain.Hour
import ru.mozgolom112.weatherapp.viewholders.HourItemViewHolder

class HourItemAdapter() :
    ListAdapter<Hour, HourItemViewHolder>(HourItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourItemViewHolder =
        HourItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: HourItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}