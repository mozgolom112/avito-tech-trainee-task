package ru.mozgolom112.weatherapp.ui.searchcity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.mozgolom112.weatherapp.R
import ru.mozgolom112.weatherapp.adapters.CitiesItemAdapter
import ru.mozgolom112.weatherapp.databinding.FragmentSearchCityBinding
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.repository.location.LocationProvider
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
        val locationProvider = LocationProvider(requireContext())
        val viewModelFactory = SearchCityViewModelFactory(geocoder, locationProvider)
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
        setClickListeners(binding)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCityBinding =
        DataBindingUtil.inflate(inflater, layout, container, false)

    private fun setObserver(binding: FragmentSearchCityBinding) = viewModel.apply {
        availableCities.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
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
        errorMessage.observe(viewLifecycleOwner){ errorMessage->
            if (errorMessage!=null){
                showSnack(errorMessage)
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

    private fun fulfillBinding(binding: FragmentSearchCityBinding) = binding.apply {
        lifecycleOwner = viewLifecycleOwner
        recycleAvailableCities.apply {
            adapter = citiesItemAdapter
        }
    }

    private fun setClickListeners(binding: FragmentSearchCityBinding) = binding.apply {
        btnBack.setOnClickListener { navigateBack() }

        btnGeoLocation.setOnClickListener {
            getLastLocation()
        }

        txtvCitySearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("SearchFragment", "${input.toString()}")
                viewModel.updateAvailableCities(input.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun navigateBack() {
        findNavController().popBackStack()
        viewModel.doneNavigating()
    }

    private fun navigateToWeatherDetailsFragment(selectedCity: City) {
        val action = SearchCityFragmentDirections.actionSearchCityFragmentToWeatherDetailsFragment(
            selectedCity
        )
        findNavController().navigate(action)
        viewModel.doneNavigating()
    }

    //SnackBar
    private fun showSnack(message: String){
        Snackbar.make(
            requireContext(),
            requireView(),
            message,
            Snackbar.LENGTH_LONG
        ).show()
        viewModel.showErrorMessage()
    }

    //location
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                viewModel.getCurrentLocation()
            } else {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Turn on location",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Настройки") {
                        Intent(Settings.ACTION_APPLICATION_SETTINGS)
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }
                    .show()
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private val PERMISSION_ID = 112

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }
}