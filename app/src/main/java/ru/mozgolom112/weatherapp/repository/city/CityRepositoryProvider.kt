package ru.mozgolom112.weatherapp.repository.city

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mozgolom112.weatherapp.database.CityDao
import ru.mozgolom112.weatherapp.database.CityDatabase
import ru.mozgolom112.weatherapp.database.dbentities.CityEntity
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.consts.IS_CURRENT_FALSE
import ru.mozgolom112.weatherapp.utils.consts.IS_CURRENT_TRUE
import ru.mozgolom112.weatherapp.utils.consts.IS_SAVED_FALSE
import ru.mozgolom112.weatherapp.utils.consts.IS_SAVED_TRUE
import ru.mozgolom112.weatherapp.utils.utilcasts.asDatabaseModel
import ru.mozgolom112.weatherapp.utils.utilcasts.asDomainModel
import java.lang.Exception

class CityRepositoryProvider(
    context: Context,
    private val database: CityDao = CityDatabase.getInstance(context).cityDao
) : CityRepositoryProviderInterface {

    override suspend fun getCurrentCity(): City? =
        withContext(Dispatchers.IO) { getCurrentCityEntity()?.asDomainModel() }

    override fun getListOfSavedCity(): LiveData<List<City>?> =
        database.getAllCities().asDomainModel()

    override suspend fun isCitySaved(city: City): Boolean = withContext(Dispatchers.IO){
        database.getCity(city.lat, city.lon)?.isSaved != IS_SAVED_FALSE
    }



    override suspend fun insertCityAsCurrent(city: City) {
        val currentCity = getCurrentCityEntity()
        if (currentCity != null) {
            currentCity.isCurrent = IS_CURRENT_FALSE
            insertOrUpdateCity(currentCity)
        }
        val updatedCity = getCityEntity(city) ?: city.asDatabaseModel()
        updatedCity.isCurrent = IS_CURRENT_TRUE
        insertOrUpdateCity(updatedCity)
    }

    override suspend fun insertCityAsSaved(city: City) {
        val updatedCity = getCityEntity(city) ?: city.asDatabaseModel()
        updatedCity.isSaved = IS_SAVED_TRUE
        insertOrUpdateCity(updatedCity)
    }

    override suspend fun insertCityAsOppositeSaved(city: City) {
        val updatedCity = getCityEntity(city) ?: city.asDatabaseModel(isSaved = IS_SAVED_FALSE)
        updatedCity.isSaved =
            if (updatedCity.isSaved == IS_SAVED_FALSE) IS_SAVED_TRUE else IS_SAVED_FALSE
        insertOrUpdateCity(updatedCity)
    }

    override suspend fun removeSavedCity(city: City) {
        database.deleteSavedCity(city.asDatabaseModel())
    }

    private suspend fun insertOrUpdateCity(city: CityEntity) = withContext(Dispatchers.IO) {
        try {
            database.insertCity(city)
        } catch (e: Exception) {
            Log.i("insertCity", "$e")
            database.updateCity(city)
        }
    }

    private suspend fun getCityEntity(city: City): CityEntity? = withContext(Dispatchers.IO) {
        database.getCity(city.lat, city.lon)
    }

    private suspend fun getCurrentCityEntity(): CityEntity? =
        withContext(Dispatchers.IO) { database.getCurrentCity() }
}