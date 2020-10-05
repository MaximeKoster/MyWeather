package com.example.whatstheweather.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.whatstheweather.R
import com.example.whatstheweather.data.models.WeatherForecastResponse
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ApiService {
    fun generateCityCard(context: Context?, card: View, weather: WeatherForecastResponse) {
        val format = DecimalFormat("0.#")
        val layoutInflater = LayoutInflater.from(context)
        if (context != null) {
            card.findViewById<TextView>(R.id.city_name).text = weather.city.name
            card.findViewById<TextView>(R.id.wind_speed).text = context.getString(R.string.wind_speed_ms, format.format(weather.list[0].wind.speed))
            card.findViewById<TextView>(R.id.temp_celcius).text = context.getString(R.string.temp_celcius, format.format(weather.list[0].main.temp))
            card.findViewById<TextView>(R.id.temp_far).text = context.getString(R.string.temp_far, format.format(weather.list[0].main.temp.times(9/5).plus(32)))
            card.findViewById<TextView>(R.id.pressure).text = context.getString(R.string.pressure, format.format(weather.list[0].main.pressure))
            card.findViewById<TextView>(R.id.humidity).text = context.getString(R.string.humidity, format.format(weather.list[0].main.humidity))

            for (forecast in weather.list) {
                val smallCard = layoutInflater.inflate(R.layout.small_weather_card, null)
                smallCard.findViewById<TextView>(R.id.temp_celcius).text = context.getString(R.string.temp_celcius, format.format(forecast.main.temp))
                smallCard.findViewById<TextView>(R.id.min_max_celcius).text = context.getString(R.string.min_max_celcius, format.format(forecast.main.temp_min), format.format(forecast.main.temp_max))
                smallCard.findViewById<TextView>(R.id.temp_far).text = context.getString(R.string.temp_far, format.format(forecast.main.temp.times(9/5).plus(32)))
                smallCard.findViewById<TextView>(R.id.min_max_far).text = context.getString(R.string.min_max_far, format.format(forecast.main.temp_min.times(9/5).plus(32)), format.format(forecast.main.temp_max.times(9/5).plus(32)))
                smallCard.findViewById<TextView>(R.id.wind_speed).text = context.getString(R.string.wind_speed_ms, format.format(forecast.wind.speed))

                var baseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                Log.i("string : ", forecast.toString())
                val date = LocalDateTime.parse(forecast.dt_txt, baseFormatter)

                smallCard.findViewById<ImageView>(R.id.wind).rotation = forecast.wind.deg
                smallCard.findViewById<TextView>(R.id.date).text = context.getString(R.string.date, date.hour.toString(), date.dayOfMonth.toString(), date.monthValue.toString())
                val icon = smallCard.findViewById<ImageView>(R.id.forecast_icon)
                val url = "https://openweathermap.org/img/wn/" + forecast.weather[0].icon + "@2x.png"
                Log.i("string : ", url)
                Picasso.get().load(url).resize(100, 100).into(icon)

                val scroll : LinearLayout = card.findViewById(R.id.forecast_cards)
                scroll.addView(smallCard)
            }

            card.visibility = View.VISIBLE
        }
    }

}