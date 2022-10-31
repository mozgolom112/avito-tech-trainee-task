package ru.mozgolom112.weatherapp.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.databinding.ItemSavedCityBinding
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.extensions.setGoneOrVisible

class CityItemViewHolder private constructor(private val binding: ItemSavedCityBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup) = CityItemViewHolder(getBinding(parent))

        private fun getBinding(parent: ViewGroup): ItemSavedCityBinding {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSavedCityBinding.inflate(inflater, parent, false)
            return binding
        }
    }
    fun bind(
        _city: City,
        isCoordinateGone: Boolean = true,
        isBtnRemoveGone: Boolean = true,
        onBtnRemoveClick: (City) -> Unit,
        onViewClick: (City) -> Unit
    ){
        binding.apply {
            city = _city
            txtvLat.setGoneOrVisible(isCoordinateGone)
            txtvLon.setGoneOrVisible(isCoordinateGone)
            btnRemoveSavedCity.setGoneOrVisible(isBtnRemoveGone)
            if (!isBtnRemoveGone) btnRemoveSavedCity.setOnClickListener {
                onBtnRemoveClick(city as City)
            }
            itemView.setOnClickListener{
                onViewClick(city as City)
            }
        }

    }
}