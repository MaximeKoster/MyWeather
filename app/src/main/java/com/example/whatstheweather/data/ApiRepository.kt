package com.example.whatstheweather.data

import com.example.whatstheweather.data.models.WeatherForecastResponse
import com.example.whatstheweather.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRepository {
    @GET("weather")
    fun getCurrentWeatherByCity(@Query("q") city: String): Call<WeatherResponse>

    @GET("forecast")
    fun get5DaysForecastByCity(@Query("q") city: String): Call<WeatherForecastResponse>
}