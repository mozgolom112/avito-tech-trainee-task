package ru.mozgolom112.weatherapp.ui.weatherdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.adapters.HourItemAdapter
import ru.mozgolom112.weatherapp.adapters.diffcallbacks.HourItemDiffCallback
import ru.mozgolom112.weatherapp.databinding.FragmentWeatherDetailsBinding
import ru.mozgolom112.weatherapp.domain.DailyWeather

class WeatherDetailsFragment : Fragment() {

    private val viewModel: WeatherDetailsViewModel by lazy { initViewModel() }

    private val hourItemAdapter: HourItemAdapter by lazy { HourItemAdapter() }

    private fun initViewModel(): WeatherDetailsViewModel {
        val viewModel: WeatherDetailsViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWeatherDetailsBinding = initBinding(inflater, container)
        setObservers(binding)
        fulfillBinding(binding)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherDetailsBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_weather_details, container, false)


    private fun setObservers(binding: FragmentWeatherDetailsBinding) {
        viewModel.apply {
            selectedDayWeather.observe(viewLifecycleOwner) { dailyWeather ->
                updateDetailUi(binding, dailyWeather)
                hourItemAdapter.submitList(dailyWeather?.hours)
            }
        }
    }

    private fun updateDetailUi(
        binding: FragmentWeatherDetailsBinding,
        dailyWeather: DailyWeather?
    ) {
        binding.apply {
            firstWeatherDetail.weatherDetail = dailyWeather?.weatherParameters?.get(0)
            secondWeatherDetail.weatherDetail = dailyWeather?.weatherParameters?.get(1)
        }
    }

    private fun fulfillBinding(binding: FragmentWeatherDetailsBinding) {
        //binding.include.weatherDetail =viewModel.selectedDay.value?.weatherParameters?.get(1)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = this@WeatherDetailsFragment.viewModel

            recyclervHours.apply {
                adapter = hourItemAdapter
            }

            tabLayoutDayForecastPicker.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.i("Tab", "${tab?.text}")
                    Log.i("Tab", "${tab?.position}")
                    when (tab?.position) {
                        0 -> viewModel.onTodayTabClick()
                        1 -> viewModel.onTomorrowTabClick()
                        2 -> viewModel.onSevenDayForecastTabClick()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

    }
}