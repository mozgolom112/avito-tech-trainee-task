package ru.mozgolom112.weatherapp.database.dbentities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mozgolom112.weatherapp.utils.consts.*
import java.util.*

@Entity(tableName = TABLE_CITY, primaryKeys = [COLUMN_CITY_LATITUDE, COLUMN_CITY_LONGITUDE])
data class CityEntity(
    @ColumnInfo(name = COLUMN_CITY_LATITUDE)
    var lat: Double,
    @ColumnInfo(name = COLUMN_CITY_LONGITUDE)
    var lon: Double,
    @ColumnInfo(name = COLUMN_CITY_NAME)
    var cityName: String,
    @ColumnInfo(name = COLUMN_CITY_COUNTRY_NAME)
    var countryName: String,
    @ColumnInfo(name = COLUMN_CITY_IS_CURRENT)
    var isCurrent: Int = 0, //true to 1 and false to 0
    @ColumnInfo(name = COLUMN_CITY_IS_SAVED)
    var isSaved: Int = 0 //true to 1 and false to 0
)