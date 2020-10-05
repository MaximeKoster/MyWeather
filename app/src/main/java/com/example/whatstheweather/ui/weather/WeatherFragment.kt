package com.example.whatstheweather.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whatstheweather.R
import com.example.whatstheweather.data.ApiRepository
import com.example.whatstheweather.data.ApiService
import com.example.whatstheweather.data.RetrofitInstance
import com.example.whatstheweather.data.UiService
import com.example.whatstheweather.data.models.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {
    private lateinit var apiRepository: ApiRepository
    private var apiService = ApiService()
    private var uiService = UiService()
    private lateinit var root: View

    private fun getCityWeather(cityName: String) {
        uiService.showLoader(context, root.findViewById(R.id.weather_layout))
        apiRepository.get5DaysForecastByCity(cityName)
            .enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(
                    call: Call<WeatherForecastResponse>,
                    response: Response<WeatherForecastResponse>
                ) {
                    val weather: WeatherForecastResponse? = response.body()
                    val card = root.findViewById<View>(R.id.city_card_details)
                    if (weather != null) {
                        apiService.generateCityCard(context, card, weather)
                        uiService.hideLoader(root.findViewById(R.id.weather_layout))
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    uiService.hideLoader(root.findViewById(R.id.weather_layout))
                    Log.i("Error -->", t.message)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_weather, container, false)
        apiRepository = RetrofitInstance.getRetrofitInstance().create(ApiRepository::class.java)

        getCityWeather(arguments?.getString("cityName").toString())

        return root
    }
}