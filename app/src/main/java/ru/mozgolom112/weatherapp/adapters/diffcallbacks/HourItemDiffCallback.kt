package ru.mozgolom112.weatherapp.adapters.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import ru.mozgolom112.weatherapp.domain.Hour

object HourItemDiffCallback : DiffUtil.ItemCallback<Hour>() {
    override fun areItemsTheSame(oldItem: Hour, newItem: Hour) = oldItem.time == oldItem.time
    override fun areContentsTheSame(oldItem: Hour, newItem: Hour) = oldItem == newItem
}