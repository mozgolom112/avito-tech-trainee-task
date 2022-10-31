package ru.mozgolom112.weatherapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.mozgolom112.weatherapp.adapters.diffcallbacks.CityItemDiffCallback
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.viewholders.CityItemViewHolder

class CitiesItemAdapter(
    private val isCoordinateGone: Boolean = true,
    private val isBtnRemoveGone: Boolean = true,
    private val onViewClick: (City) -> Unit,
    private val onBtnRemoveClick: (City) -> Unit = {}
) :
    ListAdapter<City, CityItemViewHolder>(CityItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder =
        CityItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city, isCoordinateGone, isBtnRemoveGone, onBtnRemoveClick, onViewClick)
    }


}