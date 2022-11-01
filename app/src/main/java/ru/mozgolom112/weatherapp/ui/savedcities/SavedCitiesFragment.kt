package ru.mozgolom112.weatherapp.ui.savedcities

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
import ru.mozgolom112.weatherapp.adapters.CitiesItemAdapter
import ru.mozgolom112.weatherapp.databinding.FragmentSavedCitiesBinding
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.extensions.setGoneOrVisible

class SavedCitiesFragment : Fragment() {

    private val viewModel: SavedCitiesViewModel by lazy { initViewModel() }

    private val citiesItemAdapter: CitiesItemAdapter by lazy {
        val onViewItemClick = fun(city: City) {
            viewModel.navigateToWeatherDetails(city)
        }
        val onBtnRemoveClick = fun(city: City) {
            viewModel.onRemoveSavedCityClick(city)
        }
        CitiesItemAdapter(
            isCoordinateGone = true,
            isBtnRemoveGone = false,
            onViewItemClick,
            onBtnRemoveClick
        )
    }

    private fun initViewModel(): SavedCitiesViewModel {
        val currentCity = navArgs<SavedCitiesFragmentArgs>().value.currentCity
        val viewModelFactory = SavedCitiesViewModelFactory(currentCity)
        val viewModel: SavedCitiesViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    private companion object {
        const val layout = R.layout.fragment_saved_cities
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSavedCitiesBinding = initBinding(inflater, container)
        fulfillBinding(binding)
        setObserver()
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSavedCitiesBinding =
        DataBindingUtil.inflate(inflater, layout, container, false)

    private fun fulfillBinding(binding: FragmentSavedCitiesBinding) {
        binding.apply {
            setCurrentCity()
            lifecycleOwner = viewLifecycleOwner
            recyclevFavoriteCities.apply {
                adapter = citiesItemAdapter
            }
            txtvCitySearch.setOnClickListener {
                viewModel.navigateToSearchCity()
            }
            btnBack.setOnClickListener { navigateBack() }
        }
    }

    private fun setObserver() {
        viewModel.apply {
            savedCities.observe(viewLifecycleOwner) {
                if (it != null) {
                    citiesItemAdapter.submitList(it)
                }
            }
            navigateToWeatherDetailsWithCity.observe(viewLifecycleOwner) {
                if (it != null) {
                    navigateToWeatherDetailsFragment(it)
                }
            }
            isNavigatedToSearchCity.observe(viewLifecycleOwner) { isNavigated ->
                if (isNavigated) {
                    navigateToSearchCityFragment()
                }
            }
        }
    }

    private fun FragmentSavedCitiesBinding.setCurrentCity() {
        viewModel.currentCity?.let {
            currentCityItem.city = it
            currentCityItem.root.setGoneOrVisible(isGone = false)
            txtvCurrentCityLabel.setGoneOrVisible(isGone = false)
            currentCityItem.root.setOnClickListener {
                viewModel.navigateToWeatherDetails(
                    currentCityItem.city as City
                )
            }
        }
    }

    private fun navigateToSearchCityFragment() {
        val action = SavedCitiesFragmentDirections.actionSavedCitiesFragmentToSearchCityFragment()
        findNavController().navigate(action)
        viewModel.doneNavigating()
    }

    private fun navigateToWeatherDetailsFragment(selectedCity: City) {
        if (selectedCity == viewModel.currentCity) {
            navigateBack()
        } else {
            val action =
                SavedCitiesFragmentDirections.actionSavedCitiesFragmentToWeatherDetailsFragment(
                    selectedCity
                )
            findNavController().navigate(action)
        }
        viewModel.doneNavigating()
    }

    private fun navigateBack() {
        findNavController().popBackStack()
        viewModel.doneNavigating()
    }
}