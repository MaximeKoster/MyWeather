package com.example.whatstheweather.data

import android.content.Context
import android.util.Log
import com.example.whatstheweather.data.models.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.log


class CitySharedPreferences(context: Context) {
    // Serialize object with gson
    private val gson = Gson()

    // Constant to handle shared preferences
    val GLOBAL_SHARED_PREFERENCE_NAME = "GlobalSharedPreference"

    val STORED_CITIES = "stored_cities"

    val globalSharedPreference =
        context.getSharedPreferences(GLOBAL_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = globalSharedPreference.edit()


    fun getSavedCities(): ArrayList<City> {
        val storedCitiesArray: ArrayList<City>
        val storedCitiesJson = globalSharedPreference.getString(STORED_CITIES, "[]")
        storedCitiesArray = when {
            storedCitiesJson.isNullOrEmpty() -> ArrayList()
            storedCitiesJson.contains("values") -> ArrayList()
            else -> gson.fromJson(storedCitiesJson, object : TypeToken<ArrayList<City>>() {}.type)
        }

        return storedCitiesArray
    }

    fun storeNewCity(city: City) {
        val storedCities = getSavedCities()
        if (!checkIfCityIsStored(city)) {
            storedCities.add(city)
            editor.putString(STORED_CITIES, gson.toJson(storedCities))
            editor.apply()
        }
    }

    fun flushStoredCities() {
        editor.putString(STORED_CITIES, "[]")
        editor.apply()
    }

    fun removeOneStoredCity(city: City): ArrayList<City> {
        val storedCities = getSavedCities()
        return if (storedCities.contains(city)) {
            storedCities.remove(city)
            editor.putString(STORED_CITIES, gson.toJson(storedCities))
            editor.apply()
            getSavedCities()
        } else {
            getSavedCities()
        }
    }

    public fun checkIfCityIsStored(city: City): Boolean {
        val storedCities = getSavedCities()
        return storedCities.any { it.name == city.name }
    }
}