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
    fun updateSavedCity(cityEntity: CityEntity)

    //Получить весь список
    @Query("SELECT * FROM saved_city WHERE is_current = 0")
    fun getAllSavedCitiesWithoutCurrent(): LiveData<List<CityEntity>?>

    //Получить текущий город
    @Query("SELECT * FROM saved_city WHERE is_current = 1 LIMIT 1")
    fun getCurrentSavedCity(): CityEntity?
}