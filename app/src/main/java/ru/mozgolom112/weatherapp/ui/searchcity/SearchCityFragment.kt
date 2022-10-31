package ru.mozgolom112.weatherapp.ui.searchcity

import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.adapters.CitiesItemAdapter
import ru.mozgolom112.weatherapp.databinding.FragmentSearchCityBinding
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.extensions.setGoneOrVisible
import java.util.*

class SearchCityFragment : Fragment() {

    private val viewModel: SearchCityViewModel by lazy { initViewModel() }

    private val citiesItemAdapter: CitiesItemAdapter by lazy {
        val onViewItemClick = fun(city: City) {
            viewModel.navigateToWeatherDetails(city)
        }
        CitiesItemAdapter(
            isCoordinateGone = false,
            isBtnRemoveGone = true,
            onViewItemClick
        )
    }

    private fun initViewModel(): SearchCityViewModel {
        val geocoder = Geocoder(requireContext(), Locale("RU"))
        val viewModelFactory = SearchCityViewModelFactory(geocoder)
        val viewModel: SearchCityViewModel by viewModels { viewModelFactory }
        return viewModel
    }

    private companion object {
        const val layout = R.layout.fragment_search_city
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchCityBinding = initBinding(inflater, container)
        fulfillBinding(binding)
        setObserver(binding)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCityBinding =
        DataBindingUtil.inflate(inflater, layout, container, false)

    private fun setObserver(binding: FragmentSearchCityBinding) {
        viewModel.apply {
            availableCities.observe(viewLifecycleOwner) {
                if (it != null) {
                    citiesItemAdapter.submitList(it)
                    areCitiesAvailable(binding, isAvailable = true)
                } else {
                    areCitiesAvailable(binding, isAvailable = false)
                }
            }
            navigateToWeatherDetailsWithCity.observe(viewLifecycleOwner) {
                if (it != null) {
                    navigateToWeatherDetailsFragment(it)
                }
            }
        }
    }

    private fun areCitiesAvailable(binding: FragmentSearchCityBinding, isAvailable: Boolean) {
        binding.apply {
            txtvHintChooseCityLabel.text =
                if (isAvailable) resources.getString(R.string.select_city_from_the_list_below_ru)
                else resources.getString(R.string.no_city_avalible_ru)
            recycleAvailableCities.setGoneOrVisible(!isAvailable)
        }
    }

    private fun fulfillBinding(binding: FragmentSearchCityBinding) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            recycleAvailableCities.apply {
                adapter = citiesItemAdapter
            }
        }
        binding.txtvCitySearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("SearchFragment", "${input.toString()}")
                viewModel.updateAvailableCities(input.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun navigateToWeatherDetailsFragment(selectedCity: City) {
        val action = SearchCityFragmentDirections.actionSearchCityFragmentToWeatherDetailsFragment(
            selectedCity
        )
        findNavController().navigate(action)
        viewModel.doneNavigating()
    }
}