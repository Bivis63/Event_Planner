package com.innoprog.eventplanner.presentation.weather

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.eventplanner.databinding.ItemLocationBinding
import com.innoprog.eventplanner.domain.model.ForecastLocation

class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding  = ItemLocationBinding.bind(view)

    fun bind(location: ForecastLocation,clickListener:ClickListener){
        binding.locationName.text = "${location.name} (${location.country})"

       itemView.setOnClickListener {
           clickListener.onClick(location)
       }

    }

    interface ClickListener {
        fun onClick(location: ForecastLocation)
    }
}