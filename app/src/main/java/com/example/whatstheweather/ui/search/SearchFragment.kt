package com.example.whatstheweather.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.whatstheweather.R
import com.example.whatstheweather.data.*
import com.example.whatstheweather.data.models.City
import com.example.whatstheweather.data.models.WeatherForecastResponse
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var apiRepository: ApiRepository
    private var apiService = ApiService()
    private var uiService = UiService()
    private lateinit var storage: CitySharedPreferences
    lateinit var root: View
    private lateinit var currentCity: City;
    private lateinit var switch: SwitchMaterial

    private fun getCityWeather(city: String) {
        root.findViewById<TextView>(R.id.main_text).visibility = View.INVISIBLE;
        uiService.showLoader(context, root.findViewById(R.id.search_layout))
        apiRepository.get5DaysForecastByCity(city)
           .enqueue(object : Callback<WeatherForecastResponse> {
               override fun onResponse(
                   call: Call<WeatherForecastResponse>,
                   response: Response<WeatherForecastResponse>
               ) {
                   val weather: WeatherForecastResponse? = response.body()

                   val card = root.findViewById<View>(R.id.city_card)
                   if (weather != null) {
                       root.findViewById<SwitchMaterial>(R.id.switch_city_preferences).visibility = View.VISIBLE;
                       apiService.generateCityCard(context, card, weather)
                       currentCity = weather.city
                       switch.isChecked = storage.checkIfCityIsStored(weather.city)
                       uiService.hideLoader(root.findViewById(R.id.search_layout))

                   }
               }

               override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                   Log.i("Error -->", t.message)
                   uiService.hideLoader(root.findViewById(R.id.search_layout))
               }
           })
    }

    private fun handleSwitch() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (this::currentCity.isInitialized) {
                if (isChecked) {
                    storage.storeNewCity(currentCity)
                } else {
                    storage.removeOneStoredCity(currentCity)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storage = CitySharedPreferences(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_search, container, false)
        apiRepository = RetrofitInstance.getRetrofitInstance().create(ApiRepository::class.java)
        val textView: TextView = root.findViewById(R.id.main_text)
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        switch = root.findViewById<SwitchMaterial>(R.id.switch_city_preferences)

        val searchtextView: TextView = root.findViewById(R.id.search_text)
        searchViewModel.searchText.observe(viewLifecycleOwner, Observer {
            searchtextView.text = it
        })

        root.findViewById<EditText>(R.id.search_text)
            .setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        lifecycleScope.launch {
                            getCityWeather(searchtextView.text.toString())
                        }
                        true
                    }
                    else -> false
                }
            }

        handleSwitch()

        return root
    }
}