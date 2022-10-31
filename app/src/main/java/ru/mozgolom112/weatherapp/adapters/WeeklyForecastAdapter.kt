package ru.mozgolom112.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.databinding.ItemDailyForecastBinding
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.utils.extensions.getFirst
import ru.mozgolom112.weatherapp.utils.extensions.getSecond
import ru.mozgolom112.weatherapp.utils.extensions.getThird

class WeeklyForecastAdapter(
    private val context: Context,
    private val dataSource: List<DailyWeather>
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int) = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemBinding = initBinding(parent)
        fulfillBinding(itemBinding, getItem(position))
        return itemBinding.root
    }

    private fun initBinding(
        container: ViewGroup?
    ): ItemDailyForecastBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_daily_forecast, container, false)

    private fun fulfillBinding(itemBinding: ItemDailyForecastBinding, item: DailyWeather) {
        itemBinding.apply {
            dailyWeather = item
            updateParameter(this, item)
        }
    }

    private fun updateParameter(
        itemDailyForecastBinding: ItemDailyForecastBinding,
        item: DailyWeather
    ) {
        itemDailyForecastBinding.apply {
            firstWeatherDetail.weatherParameter = item.weatherParameters.getFirst()
            secondWeatherDetail.weatherParameter = item.weatherParameters.getSecond()
            thirdWeatherDetail.weatherParameter = item.weatherParameters.getThird()
        }

    }
}