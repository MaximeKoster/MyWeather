package com.example.whatstheweather.data.models

data class WeatherForecastResponse(
    val cod: Int,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherResponse>,
    val city: City
)