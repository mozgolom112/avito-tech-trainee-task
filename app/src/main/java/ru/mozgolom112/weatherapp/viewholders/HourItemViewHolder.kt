package ru.mozgolom112.weatherapp.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mozgolom112.weatherapp.databinding.ItemHourForecastBinding
import ru.mozgolom112.weatherapp.domain.Hour

class HourItemViewHolder private constructor(private val binding: ItemHourForecastBinding) :
    RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HourItemViewHolder = HourItemViewHolder(getBinding(parent))

            private fun getBinding(parent: ViewGroup): ItemHourForecastBinding {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHourForecastBinding.inflate(layoutInflater, parent, false)
                return binding
            }
        }

    fun bind(_hour: Hour){
        binding.apply {
            hour = _hour
            executePendingBindings()
        }
    }
}