package ru.mozgolom112.weatherapp.adapters.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import ru.mozgolom112.weatherapp.domain.City

object CityItemDiffCallback: DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
}