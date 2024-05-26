package com.innoprog.eventplanner.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.eventplanner.R
import com.innoprog.eventplanner.domain.model.ForecastLocation
import com.innoprog.eventplanner.domain.model.Location

class WeatherAdapter(private val clickListener: WeatherViewHolder.ClickListener): RecyclerView.Adapter<WeatherViewHolder>() {

    var locations = ArrayList<ForecastLocation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location,parent,false )
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder.bind(locations[position],clickListener)
    }

    fun setLocation(newLocation: List<ForecastLocation>?) {
        val diffCallback = LocationDiffCallback(locations, newLocation ?: emptyList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        locations.clear()
        if (!newLocation.isNullOrEmpty()) {
            locations.addAll(newLocation)
        }
        diffResult.dispatchUpdatesTo(this)
    }
}