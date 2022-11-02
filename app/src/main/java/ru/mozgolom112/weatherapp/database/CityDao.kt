package ru.mozgolom112.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mozgolom112.weatherapp.database.dbentities.CityEntity

@Dao
interface CityDao {
    //Вставить запись
    @Insert
    fun insertCity(cityEntity: CityEntity)

    //Удалить запись
    @Delete
    fun deleteSavedCity(cityEntity: CityEntity)

    //Обновить запись
    @Update
    fun updateCity(cityEntity: CityEntity)

    //Получить по ключу
    @Query("SELECT * FROM saved_city WHERE latitude = :lat AND longitude = :lon")
    fun getCity(lat: Double, lon: Double): CityEntity?

    //Получить весь список
    @Query("SELECT * FROM saved_city WHERE is_saved = 1")
    fun getAllCities(): LiveData<List<CityEntity>?>

    //Получить весь список
    @Query("SELECT * FROM saved_city WHERE is_saved = 1 and is_current = 0")
    fun getAllCitiesWithoutCurrent(): LiveData<List<CityEntity>?>

    //Получить текущий город
    @Query("SELECT * FROM saved_city WHERE is_current = 1 LIMIT 1")
    fun getCurrentCity(): CityEntity?
}