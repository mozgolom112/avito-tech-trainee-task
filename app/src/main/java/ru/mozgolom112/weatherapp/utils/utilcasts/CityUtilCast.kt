package ru.mozgolom112.weatherapp.utils.utilcasts

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.mozgolom112.weatherapp.database.dbentities.CityEntity
import ru.mozgolom112.weatherapp.domain.City
import ru.mozgolom112.weatherapp.utils.consts.IS_CURRENT_FALSE
import ru.mozgolom112.weatherapp.utils.consts.IS_SAVED_FALSE
import ru.mozgolom112.weatherapp.utils.consts.IS_SAVED_TRUE

@JvmName("asDomainModelCityEntity")
fun CityEntity.asDomainModel(): City = mapDatabaseCityToDomain(this)

@JvmName("asDomainModelCityEntity")
fun List<CityEntity>.asDomainModel(): List<City> = map { mapDatabaseCityToDomain(it) }

fun LiveData<List<CityEntity>?>.asDomainModel(): LiveData<List<City>?> = Transformations.map(this){
    it?.asDomainModel()
}

fun City.asDatabaseModel(isCurrent: Int = IS_CURRENT_FALSE, isSaved: Int = IS_SAVED_FALSE) = CityEntity(
    lat = this.lat,
    lon = this.lon,
    cityName = this?.cityName ?:"",
    countryName = this?.country ?:"",
    isCurrent = isCurrent,
    isSaved = isSaved
)

fun mapDatabaseCityToDomain(it: CityEntity) =
    City(
        lat = it.lat,
        lon = it.lon,
        cityName = it.cityName,
        country = it.countryName
    )

fun List<Address>.asDomainModelWithLocalityNullCheck(): List<City> {
    val list = mutableListOf<City>()
    for (addr in this) {
        if (addr.locality != null) {
            list.add(
                City(
                    lat = addr.latitude,
                    lon = addr.longitude,
                    cityName = addr.locality,
                    country = addr.countryName
                )
            )
        }
    }
    return list
}

fun List<Address>.asDomainModel(): List<City> = map {
    City(
        lat = it.latitude,
        lon = it.longitude,
        cityName = it.locality,
        country = it.countryName
    )
}
