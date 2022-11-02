package ru.mozgolom112.weatherapp.repository.city

import androidx.lifecycle.LiveData
import ru.mozgolom112.weatherapp.domain.City

interface CityRepositoryProviderInterface {
    suspend fun getCurrentCity(): City?
    fun getListOfSavedCity(): LiveData<List<City>?>
    suspend fun isCitySaved(city: City): Boolean
    suspend fun insertCityAsCurrent(city: City)
    suspend fun insertCityAsSaved(city: City)
    suspend fun insertCityAsOppositeSaved(city: City)
    suspend fun removeSavedCity(city: City)


}