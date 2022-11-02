package ru.mozgolom112.weatherapp.ui.weatherdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.adapters.HourItemAdapter
import ru.mozgolom112.weatherapp.database.CityDatabase
import ru.mozgolom112.weatherapp.databinding.FragmentWeatherDetailsBinding
import ru.mozgolom112.weatherapp.domain.DailyWeather
import ru.mozgolom112.weatherapp.repository.city.CityRepositoryProvider
import ru.mozgolom112.weatherapp.utils.bindingadapters.setFavoriteIcon
import ru.mozgolom112.weatherapp.utils.extensions.getFirst
import ru.mozgolom112.weatherapp.utils.extensions.getSecond
import ru.mozgolom112.weatherapp.utils.extensions.setGoneOrVisible

class WeatherDetailsFragment : Fragment() {

    private val viewModel: WeatherDetailsViewModel by lazy { initViewModel() }

    private val hourItemAdapter: HourItemAdapter by lazy { HourItemAdapter() }

    private fun initViewModel(): WeatherDetailsViewModel {
        var selectedCity = navArgs<WeatherDetailsFragmentArgs>().value.selectedCity
        //val database = CityDatabase.getInstance(requireContext()).cityDao
        val viewModelFactory = WeatherDetailsViewModelFactory(requireContext(), selectedCity)
        val viewModel: WeatherDetailsViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    private companion object {
        const val layoutId = R.layout.fragment_weather_details
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
        DataBindingUtil.inflate(inflater, layoutId, container, false)


    private fun setObservers(binding: FragmentWeatherDetailsBinding) {
        viewModel.apply {
            selectedDayWeather.observe(viewLifecycleOwner) { dailyWeather ->
                updateDetailUi(binding, dailyWeather)
                hourItemAdapter.submitList(dailyWeather?.hours)
                resetErrorMessage()
            }
            navigateToWeeklyForecast.observe(viewLifecycleOwner) { isNavigated ->
                if (isNavigated) {
                    navigateToWeeklyForecastFragment()
                }
            }
            navigateToSavedCities.observe(viewLifecycleOwner) { isNavigated ->
                if (isNavigated) {
                    navigateToSavedCitiesFragment()
                }
            }
            favorite.observe(viewLifecycleOwner) {
                if (it != null) {
                    setFavoriteIcon(binding.btnFavority, it)
                }
            }
            errorConnection.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it == "LOADING") {
                        //Установка обычного экрана загрузки
                        setLoadingScreen(binding)
                    } else {
                        //Установка экрана с ошибкой
                        setErrorScreen(binding)
                    }
                } else {
                    binding.loadingScreen.root.setGoneOrVisible(true)
                }
            }
        }
    }

    private fun WeatherDetailsViewModel.setErrorScreen(
        binding: FragmentWeatherDetailsBinding
    ) {
        binding.loadingScreen.apply {
            txtvLoading.text = getString(R.string.error_internet_connection_ru)
            root.setGoneOrVisible(false)
            btnRetry.setGoneOrVisible(false)
            btnRetry.setOnClickListener {
                uploadForecast()
                txtvLoading.text = getString(R.string.loading_warning_ru)
            }
        }
    }

    private fun setLoadingScreen(binding: FragmentWeatherDetailsBinding) {
        binding.loadingScreen.apply {
            root.setGoneOrVisible(false)
            btnRetry.setGoneOrVisible(true)
        }
    }

    private fun updateDetailUi(
        binding: FragmentWeatherDetailsBinding,
        dailyWeather: DailyWeather?
    ) {
        binding.apply {
            txtvCityName.text = viewModel.selectedCity.cityName
            txtvCountry.text = viewModel.selectedCity.country
            firstWeatherDetail.weatherParameter = dailyWeather?.weatherParameters.getFirst()
            secondWeatherDetail.weatherParameter = dailyWeather?.weatherParameters.getSecond()
        }
    }

    private fun fulfillBinding(binding: FragmentWeatherDetailsBinding) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = this@WeatherDetailsFragment.viewModel

            recyclervHours.apply {
                adapter = hourItemAdapter
            }

            setOnTabSelectedListener()
        }

    }

    private fun FragmentWeatherDetailsBinding.setOnTabSelectedListener() {
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


    //Navigation
    private fun navigateToSavedCitiesFragment() {
        Log.i("WeatherDetails", "navigateToSavedCitiesFragment ENTRY")
        val action =
            WeatherDetailsFragmentDirections.actionWeatherDetailsFragmentToSavedCitiesFragment(
                viewModel.selectedCity
            )
        findNavController().navigate(action)
        Log.i("WeatherDetails", "navigateToSavedCitiesFragment DONE")
        viewModel.doneNavigating()
    }

    private fun navigateToWeeklyForecastFragment() {
        val action =
            WeatherDetailsFragmentDirections.actionWeatherDetailsFragmentToWeeklyForecastFragment(
                viewModel.selectedCity
            )
        findNavController().navigate(action)
        viewModel.doneNavigating()
    }
}