package ru.mozgolom112.weatherapp.repository.city

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.mozgolom112.weatherapp.database.CityDatabase
import ru.mozgolom112.weatherapp.database.dbentities.CityEntity
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.consts.IS_CURRENT_FALSE
import ru.mozgolom112.weatherapp.utils.consts.IS_CURRENT_TRUE
import ru.mozgolom112.weatherapp.utils.consts.IS_SAVED_TRUE
import ru.mozgolom112.weatherapp.utils.utilcasts.asDatabaseModel
import ru.mozgolom112.weatherapp.utils.utilcasts.asDomainModel
import java.lang.Exception

class CityRepository(context: Context, private val database: CityDatabase = CityDatabase.getInstance(context)) {

    suspend fun getCurrentCity(): City? = getCurrentCityEntity()?.asDomainModel()
    suspend fun setCurrentCity(city: City) {
        val currentCity = getCurrentCityEntity()
        if (currentCity != null){
            currentCity.isCurrent = IS_CURRENT_FALSE
            updateSavedCity(currentCity)
        }
        val updatedCurrentCity = city.asDatabaseModel(IS_CURRENT_TRUE)
        updateSavedCity(updatedCurrentCity)
    }

    suspend fun insertCityAsSaved(city: City) {
        val cityEntity = city.asDatabaseModel(isSaved = IS_SAVED_TRUE)
        try{
            database.cityDao.updateSavedCity(cityEntity)
        } catch (e: Exception){
            Log.i("insertCityAsSaved","$e")
        }

    }

    suspend fun removeSavedCity(city: City){
        val cityEntity = city.asDatabaseModel(isSaved = IS_SAVED_TRUE)
        database.cityDao.deleteSavedCity(cityEntity)
    }

    private suspend fun updateSavedCity(city: CityEntity){
        database.cityDao.updateSavedCity(city)
    }

    private suspend fun getCurrentCityEntity(): CityEntity? = database.cityDao.getCurrentSavedCity()
}