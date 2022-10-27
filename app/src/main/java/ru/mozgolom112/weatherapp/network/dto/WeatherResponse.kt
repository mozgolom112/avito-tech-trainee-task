package ru.mozgolom112.weatherapp.network.dto

//https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html
import com.squareup.moshi.Json

data class NetworkWeather(
    @Json(name = "now")
    val now: Int,
    @Json(name = "now_dt")
    val now_dt: String,
    @Json(name = "info")
    val city_info: CityInfo,
    @Json(name = "fact")
    val fact: Fact,
    @Json(name = "forecasts")
    val forecasts: List<Forecast>,
)

data class CityInfo(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "name")
    val name: String?,
    @Json(name = "abbr")
    val abbr: String?,
    @Json(name = "url")
    val url: String?
)

data class Fact(
    @Json(name = "temp")
    val temp: Int?,
    @Json(name = "temp_min")
    val temp_min: Int?,
    @Json(name = "temp_max")
    val temp_max: Int?,
    @Json(name = "temp_avg")
    val temp_avg: Int?,
    @Json(name = "feels_like")
    val feels_like: Int,
    @Json(name = "temp_water")
    val temp_water: Int?,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "condition")
    val condition: String,
    @Json(name = "wind_speed")
    val wind_speed: Double,
    @Json(name = "wind_gust")
    val wind_gust: Double,
    @Json(name = "wind_dir")
    val wind_dir: String,
    @Json(name = "pressure_mm")
    val pressure_mm: Int,
    @Json(name = "pressure_pa")
    val pressure_pa: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "is_thunder")
    val is_thunder: Boolean?,
    @Json(name = "cloudness")
    val cloudness: Double,
    @Json(name = "phenom_icon")
    val phenom_icon: String?,
    @Json(name = "phenom_condition")
    val phenom_condition: String?
)

data class Forecast(
    @Json(name = "date")
    val date: String,
    @Json(name = "week")
    val week: Int,
    @Json(name = "sunrise")
    val sunrise: String,
    @Json(name = "sunset")
    val sunset: String,
    @Json(name = "moon_code")
    val moon_code: Int,
    @Json(name = "parts")
    val parts: Parts,
    @Json(name = "hours")
    val hours: List<Hour>
)

data class Parts(
    @Json(name = "day_short")
    val day_short: Fact,
    @Json(name = "night_short")
    val night_short: Fact
)

data class Hour(
    @Json(name = "hour")
    val hour: String,
    @Json(name = "hour_ts")
    val hour_ts: String,
    @Json(name = "temp")
    val temp: Int?,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "condition")
    val condition: String
)
