package com.example.whatstheweather.ui.preferences

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatstheweather.R
import com.example.whatstheweather.data.CitySharedPreferences
import com.example.whatstheweather.data.models.City

class PreferencesFragment : Fragment() {
    private lateinit var storage: CitySharedPreferences
    private lateinit var cityList: ArrayList<City>
    private lateinit var recyclerPreferenceAdapter: RecyclerPreferenceAdapter

    private val onDeleteBtnClick: (City) -> Unit = { city ->
        storage.removeOneStoredCity(city)
    }

    private val onCardClick: (City, View) -> Unit = { city, recyclerView ->
        val bundle = bundleOf("cityName" to city.name)
        recyclerView.findNavController().navigate(R.id.action_navigation_preferences_to_navigation_weather, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preferences, container, false)

        val preferencesRecyclerView =
            root.findViewById<View>(R.id.preferences_recycler_view) as RecyclerView

        createRecyclerView(container, preferencesRecyclerView)

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storage = CitySharedPreferences(context)
    }

    private fun createRecyclerView(container: ViewGroup?, preferencesRecyclerView: RecyclerView) {
        cityList = storage.getSavedCities()
        recyclerPreferenceAdapter = RecyclerPreferenceAdapter(cityList, onDeleteBtnClick, onCardClick)
        preferencesRecyclerView.adapter = recyclerPreferenceAdapter
        preferencesRecyclerView.layoutManager = LinearLayoutManager(container?.context)
    }
}
