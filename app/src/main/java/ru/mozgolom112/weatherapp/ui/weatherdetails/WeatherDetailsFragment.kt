package ru.mozgolom112.weatherapp.ui.weatherdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.databinding.FragmentWeatherDetailsBinding
import ru.mozgolom112.weatherapp.network.RetrofitClient

class WeatherDetailsFragment : Fragment() {

    private val viewModel: WeatherDetailsViewModel by lazy {
        initViewModel()
    }

    private fun initViewModel(): WeatherDetailsViewModel {
        val viewModel: WeatherDetailsViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWeatherDetailsBinding = initBinding(inflater, container)
        fulfillBinding(binding)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherDetailsBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_weather_details, container, false)


    private fun fulfillBinding(binding: FragmentWeatherDetailsBinding) {
        binding.apply {
            viewModel = this@WeatherDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

}