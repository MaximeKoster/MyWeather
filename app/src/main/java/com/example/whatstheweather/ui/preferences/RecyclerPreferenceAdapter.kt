package com.example.whatstheweather.ui.preferences

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.whatstheweather.R
import com.example.whatstheweather.data.models.City
import kotlinx.android.synthetic.main.preferences_card.view.*

class RecyclerPreferenceAdapter(
    private val cityList: ArrayList<City>,
    private val deleteBtnListener: (City) -> Unit,
    private val cardClickListener: (City, View) -> Unit
) :
    RecyclerView.Adapter<RecyclerPreferenceAdapter.RecyclerPreferenceHolder>() {

    inner class RecyclerPreferenceHolder(recyclerCardView: View) :
        RecyclerView.ViewHolder(recyclerCardView) {
        val title: TextView = recyclerCardView.preferences_recycler_card_title
        val subtitle: TextView = recyclerCardView.preferences_recycler_card_subtitle
        private val deleteBtn: ImageButton = recyclerCardView.preferences_recycler_card_delete_btn

        init {
            deleteBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    deleteBtnListener(cityList[adapterPosition])
                    cityList.remove(cityList[adapterPosition])
                    notifyItemRemoved(adapterPosition)
                    Toast.makeText(
                        recyclerCardView.context,
                        "Ville supprimée !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            recyclerCardView.setOnClickListener {
                cardClickListener(cityList[adapterPosition], recyclerCardView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerPreferenceHolder {
        val recyclerCardView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.preferences_card, parent, false)

        return RecyclerPreferenceHolder(recyclerCardView)
    }

    override fun getItemCount() = cityList.size;

    override fun onBindViewHolder(holder: RecyclerPreferenceHolder, position: Int) {
        val currentCity = cityList[position]
        holder.title.text = currentCity.name
        holder.subtitle.text = currentCity.country
    }
}
