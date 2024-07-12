package com.example.booking.ui.notification

import com.example.booking.ui.notification.RetrofitInstance.retrofit
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@Serializable
data class WeatherResponse(
    val location: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double
)

interface WeatherService {
    @GET("data/2.5/weather?appid=9e48b39f1618ac4851496b62e3367154&units=metric")
    suspend fun getTodayWeather(@Query("q") location: String): WeatherResponse
}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}

//interface WeatherService {
//    @GET("v3/wx/forecast/daily/5day")
//    suspend fun getTodayWeather(@Query("apiKey") apiKey: String = "e3b0256b41ef41e0b88163813241007", @Query("geocode") location: String, @Query("units") units: String = "m"): WeatherResponse
//}
//
//object RetrofitInstance {
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://api.weather.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val weatherService: WeatherService by lazy {
//        retrofit.create(WeatherService::class.java)
//    }
//}
//
//interface WeatherService {
//    @GET("v1/current.json")
//    suspend fun getCurrentWeather(
//        @Query("key") apiKey: String = "e3b0256b41ef41e0b88163813241007",
//        @Query("q") location: String,
//        @Query("aqi") aqi: String = "no"
//    ): WeatherApiResponse
//}
//
//@Serializable
//data class WeatherApiResponse(
//    val location: Location,
//    val current: Current
//)
//
//@Serializable
//data class Location(
//    val name: String,
//    val region: String,
//    val country: String,
//    val lat: Double,
//    val lon: Double,
//    val tz_id: String,
//    val localtime_epoch: Long,
//    val localtime: String
//)
//
//@Serializable
//data class Current(
//    val last_updated_epoch: Long,
//    val last_updated: String,
//    val temp_c: Double,
//    val temp_f: Double,
//    val is_day: Int,
//    val condition: Condition,
//    val wind_mph: Double,
//    val wind_kph: Double,
//    val wind_degree: Int,
//    val wind_dir: String,
//    val pressure_mb: Double,
//    val pressure_in: Double,
//    val precip_mm: Double,
//    val precip_in: Double,
//    val humidity: Int,
//    val cloud: Int,
//    val feelslike_c: Double,
//    val feelslike_f: Double,
//    val windchill_c: Double,
//    val windchill_f: Double,
//    val heatindex_c: Double,
//    val heatindex_f: Double,
//    val dewpoint_c: Double,
//    val dewpoint_f: Double,
//    val vis_km: Double,
//    val vis_miles: Double,
//    val uv: Double,
//    val gust_mph: Double,
//    val gust_kph: Double
//)
//
//@Serializable
//data class Condition(
//    val text: String,
//    val icon: String,
//    val code: Int
//)
//
//interface WeatherService {
//    @GET("v1/current.json")
//    suspend fun getCurrentWeather(
//        @Query("key") apiKey: String = "e3b0256b41ef41e0b88163813241007",
//        @Query("q") location: String,
//        @Query("aqi") aqi: String = "no"
//    ): WeatherApiResponse
//}
//
//
//object RetrofitInstance {
//    private const val BASE_URL = "https://api.weatherapi.com/v1/"
//
//    val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}
//
//val weatherService: WeatherService by lazy {
//    retrofit.create(WeatherService::class.java)
//}