package ru.mozgolom112.weatherapp.ui.weeklyforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.adapters.WeeklyForecastAdapter
import ru.mozgolom112.weatherapp.databinding.FragmentWeeklyForecastBinding
import ru.mozgolom112.weatherapp.utils.hidekeyboard.hideKeyboard
import ru.mozgolom112.weatherapp.utils.snackbars.showSnackBarWithMessage

class WeeklyForecastFragment : Fragment() {

    private val viewModel: WeeklyForecastViewModel by lazy { initViewModel() }

    private fun initViewModel(): WeeklyForecastViewModel {
        val selectedCity = navArgs<WeeklyForecastFragmentArgs>().value.city
        val viewModelFactory = WeeklyForecastViewModelFactory(selectedCity)
        val viewModel: WeeklyForecastViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    private companion object {
        const val layoutId = R.layout.fragment_weekly_forecast
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWeeklyForecastBinding = initBinding(inflater, container)
        setObservers(binding)
        fulfillBinding(binding)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeeklyForecastBinding =
        DataBindingUtil.inflate(inflater, layoutId, container, false)

    private fun setObservers(binding: FragmentWeeklyForecastBinding) {
        viewModel.apply {
            weeklyForecast.observe(viewLifecycleOwner) { list ->
                list?.let {
                    binding.listvWeeklyForecast.adapter =
                        WeeklyForecastAdapter(requireContext(), list)
                }
            }
            errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                if (errorMessage != null) {
                    hideKeyboard(requireActivity())
                    showSnackBar(errorMessage)
                }
            }
        }
    }

    private fun fulfillBinding(binding: FragmentWeeklyForecastBinding) {
        binding.apply {
            txtvCityName.text = viewModel.selectedCity.cityName
            btnBack.setOnClickListener { navigateBack() }
        }
    }

    //SnackBar
    private fun showSnackBar(message: String) {
        showSnackBarWithMessage(requireView(), message)
        viewModel.showErrorMessage()
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}