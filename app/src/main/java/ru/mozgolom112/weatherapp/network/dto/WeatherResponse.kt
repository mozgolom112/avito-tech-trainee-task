package ru.mozgolom112.weatherapp.network.dto

//https://yandex.ru/dev/weather/doc/dg/concepts/forecast-test.html
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWeatherContainer(val dailyForecasts: List<NetworkWeather>)

data class NetworkWeather(
    @Json(name = "now")
    val now: Int,
    @Json(name = "now_dt")
    val now_dt: String,
    @Json(name = "info")
    val city_info: NetworkCityInfo,
    @Json(name = "fact")
    val forecast_now: NetworkWeatherDetails, //Объект содержит информацию о погоде на данный момент
    @Json(name = "forecasts")
    val forecasts: List<NetworkForecast>,
)

data class NetworkCityInfo(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "tzinfo")
    val tzifno: NetworkCityTimeZoneInfo,
    @Json(name = "url")
    val url: String?
)

data class NetworkCityTimeZoneInfo(
    @Json(name = "name")
    val name: String?,
    @Json(name = "abbr")
    val abbr: String?,
)



data class NetworkWeatherDetails(

    //Температурные значения
    @Json(name = "temp")
    val temp: Int?,
    @Json(name = "temp_min")
    val temp_min: Int?,
    @Json(name = "temp_max")
    val temp_max: Int?,
    @Json(name = "temp_avg")
    val temp_avg: Int?,
    @Json(name = "feels_like")
    val feels_like: Int?,

    //Код расшифровки погодного описания и иконка погоды
    @Json(name = "icon")
    val icon: String,
    @Json(name = "condition")
    val condition: String,

    //Основные детали
    @Json(name = "wind_speed")
    val wind_speed: Double,
    @Json(name = "wind_dir")
    val wind_dir: String,
    @Json(name = "cloudness")
    val cloudness: Double,
    @Json(name = "pressure_mm")
    val pressure_mm: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "prec_type")
    val prec_type: Int,

    //вспомогательные детали
    @Json(name = "pressure_pa")
    val pressure_pa: Int,
    @Json(name = "temp_water")
    val temp_water: Int?,
    @Json(name = "is_thunder")
    val is_thunder: Boolean?,
    @Json(name = "phenom_icon")
    val phenom_icon: String?,
    @Json(name = "phenom_condition")
    val phenom_condition: String?
)

data class NetworkForecast(
    @Json(name = "date")
    val date: String,
    @Json(name = "parts")
    val parts: Parts,
    @Json(name = "sunrise")
    val sunrise: String,
    @Json(name = "sunset")
    val sunset: String,
    @Json(name = "hours")
    val hours: List<NetworkHour>?
)

data class Parts(
    @Json(name = "day_short")
    val day_short: NetworkWeatherDetails,
    @Json(name = "night_short")
    val night_short: NetworkWeatherDetails
)

data class NetworkHour(
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
